package sg.com.spgroup.friendsmgmt.api;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import sg.com.spgroup.friendsmgmt.exception.ApplicationException;
import sg.com.spgroup.friendsmgmt.exception.GeneralApplicationException;
import sg.com.spgroup.friendsmgmt.model.Friends;
import sg.com.spgroup.friendsmgmt.model.FriendsMgmtResponse;
import sg.com.spgroup.friendsmgmt.model.PostMessageParam;
import sg.com.spgroup.friendsmgmt.model.UserActionParam;
import sg.com.spgroup.friendsmgmt.model.UserEmailParam;
import sg.com.spgroup.friendsmgmt.model.UserProfileParam;
import sg.com.spgroup.friendsmgmt.model.Views;
import sg.com.spgroup.friendsmgmt.services.FriendManagementService;

@RestController
@Api(value = "/friends")
@RequestMapping(value = "/friends", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class FriendsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FriendsController.class);

    @Autowired
    FriendManagementService friendMgmtSvc;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @Transactional
    @JsonView(Views.Status.class)
    public ResponseEntity<?> createUserProfile(
	    @ApiParam(required = true) @RequestBody @Valid final UserProfileParam profile, final Errors errors) {

	if (errors.hasErrors()) {
	    LOGGER.info("error case");
	    return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
	}

	try {
	    boolean result = friendMgmtSvc.createUserProfile(profile.getEmailId().toLowerCase(), profile.getFullname());

	    final FriendsMgmtResponse response = new FriendsMgmtResponse();
	    response.setStatus(result);

	    return new ResponseEntity<>(response, HttpStatus.CREATED);
	} catch (ApplicationException e) {
	    return new ResponseEntity<>(e.getErrorMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @JsonView(Views.EmailList.class)
    public ResponseEntity<?> list() //
    {
	final FriendsMgmtResponse response = friendMgmtSvc.getAllEmailList();
	return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/connection/", method = RequestMethod.POST)
    @Transactional
    @JsonView(Views.Status.class)
    public ResponseEntity<?> createFriendConnection(@ApiParam(required = true) @RequestBody final Friends friends,
	    final Errors errors) {
	if (friends == null || friends.getFriends().size() != 2) {
	    return new ResponseEntity<>(new GeneralApplicationException("Invalid parameter").getErrorMessage(),
		    HttpStatus.BAD_REQUEST);
	}

	try {

	    final FriendsMgmtResponse response = new FriendsMgmtResponse();

	    boolean result = friendMgmtSvc.createFriendConnection(friends.getFriends().get(0).toLowerCase(),
		    friends.getFriends().get(1).toLowerCase());

	    response.setStatus(result);

	    return new ResponseEntity<>(response, HttpStatus.CREATED);
	} catch (ApplicationException e) {
	    return new ResponseEntity<>(e.getErrorMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}
    }

    @RequestMapping(value = "/connection/list/", method = RequestMethod.POST)
    @JsonView(Views.FriendsList.class)
    public ResponseEntity<?> getFriendConnection(
	    @ApiParam(required = true) @RequestBody final UserEmailParam userEmailParam, final Errors errors) {

	if (errors.hasErrors()) {
	    LOGGER.info("error case");
	    return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
	}

	try {
	    final FriendsMgmtResponse response = friendMgmtSvc
		    .getFriendConnections(userEmailParam.getEmail().toLowerCase());

	    return new ResponseEntity<>(response, HttpStatus.OK);
	} catch (ApplicationException e) {
	    return new ResponseEntity<>(e.getErrorMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}
    }

    @RequestMapping(value = "/connection/subscribe/", method = RequestMethod.POST)
    @JsonView(Views.Status.class)
    public ResponseEntity<?> subscribeToUpdates(
	    @ApiParam(required = true) @Valid @RequestBody final UserActionParam subscriptionParam,
	    final Errors errors) {
	if (errors.hasErrors()) {
	    LOGGER.info("error case");
	    return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
	}

	if (subscriptionParam.getTarget().equalsIgnoreCase(subscriptionParam.getRequestor())) {
	    return new ResponseEntity<>(
		    new GeneralApplicationException("Cannot subscribe to oneself").getErrorMessage(),
		    HttpStatus.BAD_REQUEST);
	}
	try {

	    final FriendsMgmtResponse response = new FriendsMgmtResponse();
	    boolean result = friendMgmtSvc.createFriendSubscription(subscriptionParam.getTarget().toLowerCase(),
		    subscriptionParam.getRequestor().toLowerCase());

	    response.setStatus(result);

	    return new ResponseEntity<>(response, HttpStatus.CREATED);
	} catch (ApplicationException e) {
	    return new ResponseEntity<>(e.getErrorMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}
    }

    @RequestMapping(value = "/connection/block/", method = RequestMethod.POST)
    @JsonView(Views.Status.class)
    public ResponseEntity<?> blockFriend(@ApiParam(required = true) @RequestBody final UserActionParam blockParam,
	    final Errors errors) {
	if (errors.hasErrors()) {
	    LOGGER.info("error case");
	    return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
	}

	try {

	    final FriendsMgmtResponse response = new FriendsMgmtResponse();

	    boolean result = friendMgmtSvc.blockFriendConnection(blockParam.getRequestor().toLowerCase(),
		    blockParam.getTarget().toLowerCase());

	    response.setStatus(result);

	    return new ResponseEntity<>(response, HttpStatus.CREATED);
	} catch (ApplicationException e) {
	    return new ResponseEntity<>(e.getErrorMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}
    }

    @RequestMapping(value = "/message/", method = RequestMethod.POST)
    @JsonView(Views.UpdateList.class)
    public ResponseEntity<?> postUpdates(@ApiParam(required = true) @Valid @RequestBody final PostMessageParam msgParam,
	    final Errors errors) {
	if (errors.hasErrors()) {
	    LOGGER.info("error case");
	    return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
	}

	try {
	    FriendsMgmtResponse response = friendMgmtSvc.postUpdate(msgParam.getSender().toLowerCase(),
		    msgParam.getText());

	    return new ResponseEntity<>(response, HttpStatus.OK);
	} catch (ApplicationException e) {
	    return new ResponseEntity<>(e.getErrorMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}
    }
}
