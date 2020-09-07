package tp.das.Model.Store.Adapter;

import tp.das.Model.Auth.AuthModel;
import tp.das.Model.Event.*;
import tp.das.Model.Notifications.NotificationModel;
import tp.das.Model.Store.Builder.CalendarStoreBuilder;
import tp.das.Model.Store.Model.ICalendarStoreData;
import tp.das.Model.Utilizador.UserModel;
import tp.das.Model.Utilizador.UserPreferenceModel;
import tp.das.Model.Utilizador.UserPreferencesModel;
import tp.das.Model.Utilizador.UserSchedulingPreferenceTypeEnum;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class TextCalendarDataStoreAdapter implements ICalendarDataStoreAdapter {
    private final static String PATH = "/";
    private final static boolean APPEND_TO_FILE = false;
    private final static String RECORD_SEPARATOR = "|-|";
    private final static String DATA_SEPARATOR = "|-|-|";

    @Override
    public void write(String filename, ICalendarStoreData data) throws IOException {
        FileWriter write = new FileWriter(PATH, APPEND_TO_FILE);
        PrintWriter printWriter = new PrintWriter(write);
        for (AuthModel account : data.getAccounts()) {
            printWriter.println(account.getId());
            printWriter.println(account.getUsername());
            printWriter.println(account.getPassword());
            printWriter.println(RECORD_SEPARATOR);
        }
        printWriter.println(DATA_SEPARATOR);
        for (UserModel user : data.getUsers()) {
            printWriter.println(user.getId());
            printWriter.println(user.getAccount().getId());
            printWriter.println(user.getName());
            printWriter.println(this.preferencesToString(user.getPreferences().getAcceptable()));
            printWriter.println(this.preferencesToString(user.getPreferences().getPreferred()));
            printWriter.println(RECORD_SEPARATOR);
        }
        printWriter.println(DATA_SEPARATOR);
        for (EventModel event : data.getEvents()) {
            printWriter.println(event.getId());
            printWriter.println(event.getName());
            printWriter.println(event.getCreator());
            printWriter.println(event.getParticipants().stream()
                    .map((EventParticipantModel p) -> p.getUserModel().getId() + "_" + p.getState() + "_"
                            + p.getAcceptableTimestamps().stream().map(Object::toString).collect(Collectors.joining("-")) + "_"
                            + p.getPreferredTimestamps().stream().map(Object::toString).collect(Collectors.joining("-")))
                    .collect(Collectors.joining(",")));
            printWriter.println(event.getDates().stream()
                    .map((DateModel date) -> date.getTimestamp() + "-" + date.getDuration())
                    .collect(Collectors.joining(",")));
            printWriter.println(event.getType());
            printWriter.println(RECORD_SEPARATOR);
        }
        printWriter.println(DATA_SEPARATOR);
        for (NotificationModel notification : data.getNotifications()) {
            printWriter.println(notification.getId());
            printWriter.println(notification.getUser().getId());
            printWriter.println(notification.getEvent().getId());
            printWriter.println(notification.getRead());
            printWriter.println(RECORD_SEPARATOR);
        }
        printWriter.println(DATA_SEPARATOR);
        printWriter.close();
    }

    private String preferencesToString(List<UserPreferenceModel> prefs) {
        return this.preferencesToString(prefs, ",");
    }

    private String preferencesToString(List<UserPreferenceModel> prefs, String delimiter) {
        return prefs.stream()
                .map((UserPreferenceModel pref) -> pref.getFromTimestamp() + "-" + pref.getToTimestamp() + "-" + pref.getType())
                .collect(Collectors.joining(delimiter));
    }

    @Override
    public ICalendarStoreData read(String filename) throws IOException {
        final FileReader fr = new FileReader(filename);
        final BufferedReader textReader = new BufferedReader(fr);
        final List<List<String>> accountDataBlock = this.readDataBlock(textReader);
        final List<List<String>> usersDataBlock = this.readDataBlock(textReader);
        final List<List<String>> eventsDataBlock = this.readDataBlock(textReader);
        final List<List<String>> notificationsDataBlock = this.readDataBlock(textReader);
        textReader.close();
        final Map<Long, AuthModel> accounts = new HashMap<>();
        for (List<String> accountRecord : accountDataBlock) {
            final Long accountId = Long.parseLong(accountRecord.get(0));
            accounts.put(accountId, new AuthModel(accountId, accountRecord.get(1), accountRecord.get(2)));
        }
        final Map<Long, UserModel> users = new HashMap<>();
        for (List<String> userRecord : usersDataBlock) {
            final Long userId = Long.parseLong(userRecord.get(0));
            final Long accountId = Long.parseLong(userRecord.get(1));
            final UserPreferencesModel prefs = new UserPreferencesModel();
            final String acceptableRecord = userRecord.get(3);
            prefs.setAcceptable(Arrays.stream(acceptableRecord.split(","))
                    .map((String a) -> {
                        String[] ar = a.split("-");
                        return new UserPreferenceModel(Long.parseLong(ar[0]), Long.parseLong(ar[1]),
                                UserSchedulingPreferenceTypeEnum.valueOf(ar[2]));
                    })
                    .collect(Collectors.toList()));
            final String preferredRecord = userRecord.get(4);
            prefs.setPreferred(Arrays.stream(preferredRecord.split(","))
                    .map((String a) -> {
                        String[] ar = a.split("-");
                        return new UserPreferenceModel(Long.parseLong(ar[0]), Long.parseLong(ar[1]),
                                UserSchedulingPreferenceTypeEnum.valueOf(ar[2]));
                    })
                    .collect(Collectors.toList()));
            users.put(userId, new UserModel(userId, accounts.get(accountId), userRecord.get(2), prefs));
        }
        final Map<Long, EventModel> events = new HashMap<>();
        for (List<String> eventRecord : eventsDataBlock) {
            final Long eventId = Long.parseLong(eventRecord.get(0));
            final Long creatorId = Long.parseLong(eventRecord.get(2));
            final List<EventParticipantModel> participants = Arrays.stream(eventRecord.get(3).split(","))
                    .map((String p) -> {
                        final String[] pFields = p.split("_");
                        final Long pId = Long.parseLong(pFields[0]);
                        return new EventParticipantModel(users.get(pId), EventParticipantStatesEnum.valueOf(pFields[1]),
                                Arrays.stream(pFields[2].split("-")).map(Long::parseLong).collect(Collectors.toList()),
                                Arrays.stream(pFields[3].split("-")).map(Long::parseLong).collect(Collectors.toList()));
                    }).collect(Collectors.toList());
            final List<DateModel> dates = Arrays.stream(eventRecord.get(4).split(","))
                    .map((String d) -> {
                        final String[] dFields = d.split("-");
                        return new DateModel(Long.parseLong(dFields[0]), Long.parseLong(dFields[1]));
                    })
                    .collect(Collectors.toList());
            events.put(eventId, new EventModel(eventId, eventRecord.get(1), users.get(creatorId),
                    EventTypesEnum.valueOf(eventRecord.get(5)), participants, dates));
        }
        final Map<Long, NotificationModel> notifications = new HashMap<>();
        for (List<String> notificationRecord : notificationsDataBlock) {
            final Long notificationId = Long.parseLong(notificationRecord.get(0));
            final Long userId = Long.parseLong(notificationRecord.get(1));
            final Long eventId = Long.parseLong(notificationRecord.get(2));
            notifications.put(notificationId, new NotificationModel(notificationId, users.get(userId),
                    events.get(eventId), Boolean.parseBoolean(notificationRecord.get(3))));
        }
        return new CalendarStoreBuilder()
                .setAccounts(new ArrayList<>(accounts.values()))
                .setUsers(new ArrayList<>(users.values()))
                .setEvents(new ArrayList<>(events.values()))
                .setNotifications(new ArrayList<>(notifications.values()))
                .getCalendarData();
    }

    private List<List<String>> readDataBlock(BufferedReader textReader) throws IOException {
        String valueRead = null;
        List<String> colsRead = new ArrayList<>();
        List<List<String>> linesRead = new ArrayList<>();
        while (!(valueRead = textReader.readLine()).equals(DATA_SEPARATOR)) {
            if (valueRead.equals(RECORD_SEPARATOR)) {
                linesRead.add(colsRead);
                colsRead.clear();
                continue;
            }
            colsRead.add(valueRead);
        }
        return linesRead;
    }

    @Override
    public List<String> filenames() {
        return Arrays.stream(Objects.requireNonNull(new File(PATH).listFiles()))
                .filter((File f) -> f.getName().endsWith(".txt")).map(File::toString)
                .collect(Collectors.toList());
    }
}
