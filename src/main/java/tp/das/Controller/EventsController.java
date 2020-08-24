package tp.das.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.das.DTOs.Event.*;
import tp.das.Model.Event.EventTypesEnum;
import tp.das.Service.EventsService;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/events")
public class EventsController {

    @GetMapping()
    public ResponseEntity getAll() {
        return ResponseEntity.ok(EventsService.getInstance().findAll());
    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity getById(@PathVariable("eventId") Long eventId) {
        return ResponseEntity.ok(EventsService.getInstance().findById(eventId));
    }

    @DeleteMapping(path = "/{eventId}")
    public ResponseEntity deleteById(@PathVariable("eventId") Long eventId) {
        // TODO: implement this!
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/invite")
    public ResponseEntity createInvite(@Valid @RequestBody CreateInviteEventDTO createInviteEventDTO) {
        EventsService.getInstance().createEvent(createInviteEventDTO, EventTypesEnum.INVITE);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/invite/{eventId}")
    public ResponseEntity updateInvite(@PathVariable("eventId") Long eventId,
                                       @Valid @RequestBody CreateInviteEventDTO createInviteEventDTO) {
        EventsService.getInstance().editEvent(eventId, createInviteEventDTO, EventTypesEnum.INVITE);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/invite/{eventId}")
    public ResponseEntity deleteInvite(@PathVariable("eventId") Long eventId) {
        EventsService.getInstance().deleteEvent(eventId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/scheduling")
    public ResponseEntity createScheduling(@Valid @RequestBody CreateSchedulingEventDTO createSchedulingEventDTO) {
        EventsService.getInstance().createEvent(createSchedulingEventDTO, createSchedulingEventDTO.getType());
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/scheduling/{eventId}/status")
    public ResponseEntity changeSchedulingPreferences(@PathVariable("eventId") Long eventId,
                                                      @Valid @RequestBody EventSchedulingStatusDTO statusDTO) {
        EventsService.getInstance().updateEventSchedulingStatus(eventId, statusDTO);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/invite/{eventId}/status")
    public ResponseEntity changeStatus(@PathVariable("eventId") Long eventId,
                                       @Valid @RequestBody ChangeEventStatusDTO statusDTO) {
        EventsService.getInstance().updateEventInviteStatus(eventId, statusDTO);
        return ResponseEntity.ok("changeStatus");
    }

    @PostMapping(path = "/operationRedo")
    public ResponseEntity operationRedo(@Valid @RequestBody UserOperationDTO operationDTO) {
        EventsService.getInstance().redoEventOperation(operationDTO.getUserId());
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/operationUndo")
    public ResponseEntity operationUndo(@Valid @RequestBody UserOperationDTO operationDTO) {
        EventsService.getInstance().undoEventOperation(operationDTO.getUserId());
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("not valid due to validation error: " + e.getMessage());
    }

}
