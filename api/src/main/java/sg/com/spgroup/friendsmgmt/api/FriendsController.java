package sg.com.spgroup.friendsmgmt.api;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;
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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import sg.com.spgroup.friendsmgmt.api.param.FriendsParam;
import sg.com.spgroup.friendsmgmt.api.param.PostMessageParam;
import sg.com.spgroup.friendsmgmt.api.param.UserActionParam;
import sg.com.spgroup.friendsmgmt.api.param.UserEmailParam;
import sg.com.spgroup.friendsmgmt.api.param.UserProfileParam;
import sg.com.spgroup.friendsmgmt.exception.ApplicationException;
import sg.com.spgroup.friendsmgmt.exception.GeneralApplicationException;
import sg.com.spgroup.friendsmgmt.model.ErrorMessage;
import sg.com.spgroup.friendsmgmt.model.FriendsMgmtResponse;
import sg.com.spgroup.friendsmgmt.model.Views;
import sg.com.spgroup.friendsmgmt.services.FriendManagementService;

@RestController
@Api( value = "friends" )
@RequestMapping( value = "/friends", produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
@Validated
public class FriendsController
{
    private static final Logger LOGGER = LoggerFactory.getLogger( FriendsController.class );

    @Autowired
    FriendManagementService friendMgmtSvc;

    /**
     * Creates user profile and persist it in database
     * 
     * @param profile
     *            - Profile details
     * @param errors
     *            - validation errors
     * @return return value
     */
    @RequestMapping( value = "/", method = RequestMethod.POST )
    @ApiOperation( //
            value = "Create User Profile", //
            notes = "Persists a user profile in database" ) //
    @ApiResponses( //
            value = { //
                    @ApiResponse( code = 201, message = "Data saved successfully", response = Views.Status.class ),
                    @ApiResponse( code = 422, message = "Saving failed", response = ErrorMessage.class ),
                    @ApiResponse( code = 400, message = "Invalid parameters", response = ErrorMessage.class ) } )

    @Transactional
    @JsonView( Views.Status.class )
    public ResponseEntity<?> createUserProfile( //
                                                @ApiParam( value = "User profile details", required = true ) //
                                                @RequestBody //
                                                @Valid //
                                                final UserProfileParam profile,

                                                final Errors errors )
    {

        if ( errors.hasErrors() )
        {
            LOGGER.info( "error case" );
            return new ResponseEntity<>( errors.getAllErrors(), HttpStatus.BAD_REQUEST );
        }

        try
        {
            boolean result = friendMgmtSvc.createUserProfile( profile.getEmailId().toLowerCase(),
                    profile.getFullname() );

            final FriendsMgmtResponse response = new FriendsMgmtResponse();
            response.setStatus( result );

            return new ResponseEntity<>( response, HttpStatus.CREATED );
        }
        catch ( ApplicationException e )
        {
            return new ResponseEntity<>( e.getErrorMessage(), HttpStatus.UNPROCESSABLE_ENTITY );
        }
    }

    @RequestMapping( value = "/", method = RequestMethod.GET )
    @JsonView( Views.EmailList.class )
    @ApiOperation( //
            value = "Get Registered Email Adresses", //
            notes = "Returns the list of registered email addresses" //
    ) //
    @ApiResponses( //
            value = { //
                    @ApiResponse( code = 200, message = "Email list was successfully retrieved", response = Views.EmailList.class ) } )
    public ResponseEntity<?> list() //
    {
        final FriendsMgmtResponse response = friendMgmtSvc.getAllEmailList();
        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/connection/", method = RequestMethod.POST )
    @JsonView( Views.Status.class )
    @ApiOperation( //
            value = "Create Friend Connection", //
            notes = "Create a friend connection between two email address" //
    ) //
    @ApiResponses( //
            value = { //
                    @ApiResponse( code = 201, message = "Data saved successfully", response = Views.Status.class ),
                    @ApiResponse( code = 422, message = "Saving failed", response = ErrorMessage.class ),
                    @ApiResponse( code = 400, message = "Invalid parameters", response = ErrorMessage.class ) } )
    @Transactional
    public ResponseEntity<?> createFriendConnection( //
                                                     @ApiParam( value = "Friend connection details", required = true ) //
                                                     @RequestBody //
                                                     @NotEmpty //
                                                     final FriendsParam friends,

                                                     final Errors errors )
    {
        if ( friends == null || friends.getFriends().size() != 2 )
        {
            return new ResponseEntity<>(
                    new GeneralApplicationException( "Invalid parameter" ).getErrorMessage(),
                    HttpStatus.BAD_REQUEST );
        }

        try
        {

            final FriendsMgmtResponse response = new FriendsMgmtResponse();

            boolean result = friendMgmtSvc.createFriendConnection(
                    friends.getFriends().get( 0 ).toLowerCase(),
                    friends.getFriends().get( 1 ).toLowerCase() );

            response.setStatus( result );

            return new ResponseEntity<>( response, HttpStatus.CREATED );
        }
        catch ( ApplicationException e )
        {
            return new ResponseEntity<>( e.getErrorMessage(), HttpStatus.UNPROCESSABLE_ENTITY );
        }
    }

    @RequestMapping( value = "/connection/list/", method = RequestMethod.POST )
    @JsonView( Views.FriendsList.class )
    @ApiOperation( //
            value = "Get Friend Connections", //
            notes = "Returns the list of friend connection for a specific email address" //
    ) //
    @ApiResponses( //
            value = { //
                    @ApiResponse( code = 200, message = "Data retrieved successfully", response = Views.FriendsList.class ),
                    @ApiResponse( code = 400, message = "Invalid parameters", response = ErrorMessage.class ),
                    @ApiResponse( code = 422, message = "Email address not registered", response = ErrorMessage.class ) } )
    public ResponseEntity<?> getFriendConnection( //
                                                  @ApiParam( value = "User email address", required = true ) //
                                                  @RequestBody //
                                                  @Valid //
                                                  final UserEmailParam userEmailParam,

                                                  final Errors errors )
    {

        if ( errors.hasErrors() )
        {
            LOGGER.info( "error case" );
            return new ResponseEntity<>( errors.getAllErrors(), HttpStatus.BAD_REQUEST );
        }

        try
        {
            final FriendsMgmtResponse response = friendMgmtSvc
                    .getFriendConnections( userEmailParam.getEmail().toLowerCase() );

            return new ResponseEntity<>( response, HttpStatus.OK );
        }
        catch ( ApplicationException e )
        {
            return new ResponseEntity<>( e.getErrorMessage(), HttpStatus.UNPROCESSABLE_ENTITY );
        }
    }

    @RequestMapping( value = "/connection/subscribe/", method = RequestMethod.POST )
    @ApiOperation( //
            value = "Create Subscription", //
            notes = "Creates a subscription between target and requestor email address" //
    ) //
    @ApiResponses( //
            value = { //
                    @ApiResponse( code = 201, message = "Data saved successfully", response = Views.Status.class ),
                    @ApiResponse( code = 422, message = "Saving failed", response = ErrorMessage.class ),
                    @ApiResponse( code = 400, message = "Invalid parameters", response = ErrorMessage.class ) } )
    @JsonView( Views.Status.class )
    public ResponseEntity<?> subscribeToUpdates( //
                                                 @ApiParam( value = "action details", required = true ) //
                                                 @RequestBody //
                                                 @Valid //
                                                 final UserActionParam subscriptionParam,

                                                 final Errors errors )
    {
        if ( errors.hasErrors() )
        {
            LOGGER.info( "error case" );
            return new ResponseEntity<>( errors.getAllErrors(), HttpStatus.BAD_REQUEST );
        }

        if ( subscriptionParam.getTarget().equalsIgnoreCase( subscriptionParam.getRequestor() ) )
        {
            return new ResponseEntity<>(
                    new GeneralApplicationException( "Cannot subscribe to oneself" )
                            .getErrorMessage(),
                    HttpStatus.BAD_REQUEST );
        }
        try
        {

            final FriendsMgmtResponse response = new FriendsMgmtResponse();
            boolean result = friendMgmtSvc.createFriendSubscription(
                    subscriptionParam.getTarget().toLowerCase(),
                    subscriptionParam.getRequestor().toLowerCase() );

            response.setStatus( result );

            return new ResponseEntity<>( response, HttpStatus.CREATED );
        }
        catch ( ApplicationException e )
        {
            return new ResponseEntity<>( e.getErrorMessage(), HttpStatus.UNPROCESSABLE_ENTITY );
        }
    }

    @RequestMapping( value = "/connection/block/", method = RequestMethod.POST )
    @ApiOperation( //
            value = "Create Block Connection", //
            notes = "Blocked updates from requestor to target email address, no friend connection can be done once the target is blocked" //
    ) //
    @ApiResponses( //
            value = { //
                    @ApiResponse( code = 201, message = "Data saved successfully", response = Views.Status.class ),
                    @ApiResponse( code = 422, message = "Saving failed", response = ErrorMessage.class ),
                    @ApiResponse( code = 400, message = "Invalid parameters", response = ErrorMessage.class ) } )
    @JsonView( Views.Status.class )
    public ResponseEntity<?> blockFriend( //
                                          @ApiParam( value = "action details", required = true ) //
                                          @RequestBody //
                                          @Valid final UserActionParam blockParam,

                                          final Errors errors )
    {
        if ( errors.hasErrors() )
        {
            LOGGER.info( "error case" );
            return new ResponseEntity<>( errors.getAllErrors(), HttpStatus.BAD_REQUEST );
        }

        try
        {

            final FriendsMgmtResponse response = new FriendsMgmtResponse();

            boolean result = friendMgmtSvc.blockFriendConnection(
                    blockParam.getRequestor().toLowerCase(), blockParam.getTarget().toLowerCase() );

            response.setStatus( result );

            return new ResponseEntity<>( response, HttpStatus.CREATED );
        }
        catch ( ApplicationException e )
        {
            return new ResponseEntity<>( e.getErrorMessage(), HttpStatus.UNPROCESSABLE_ENTITY );
        }
    }

    @RequestMapping( value = "/message/", method = RequestMethod.POST )
    @ApiOperation( //
            value = "Post Message", //
            notes = "Returns a list of email address eligible to receive message updates" //
    ) //
    @ApiResponses( //
            value = { //
                    @ApiResponse( code = 200, message = "Retrieved data successfully", response = Views.UpdateList.class ),
                    @ApiResponse( code = 400, message = "Invalid parameters", response = ErrorMessage.class ) } )
    @JsonView( Views.UpdateList.class )
    public ResponseEntity<?> postUpdates( //
                                          @ApiParam( value = "Message update details", required = true ) //
                                          @RequestBody //
                                          @Valid //
                                          final PostMessageParam msgParam,

                                          final Errors errors )
    {
        if ( errors.hasErrors() )
        {
            LOGGER.info( "error case" );
            return new ResponseEntity<>( errors.getAllErrors(), HttpStatus.BAD_REQUEST );
        }

        try
        {
            FriendsMgmtResponse response = friendMgmtSvc
                    .postUpdate( msgParam.getSender().toLowerCase(), msgParam.getText() );

            return new ResponseEntity<>( response, HttpStatus.OK );
        }
        catch ( ApplicationException e )
        {
            return new ResponseEntity<>( e.getErrorMessage(), HttpStatus.BAD_REQUEST );
        }
    }
}
