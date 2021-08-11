package ru.human.resources.user.service.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.human.resources.common.dao.api.UserService;
import ru.human.resources.common.data.exception.HumanResourcesException;
import ru.human.resources.common.data.UserDto;
import ru.human.resources.common.data.model.request.UserRequest;
import ru.human.resources.common.data.model.response.UserResponse;
import ru.human.resources.common.data.page.PageData;
import ru.human.resources.common.data.page.PageLink;
import ru.human.resources.dao.model.ToData;


/**
 * @author Anton Kravchenkov
 * @since 14.07.2021
 */
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/v1")
public class UserController extends BaseController {

    private static final String USER_ID = "userId";

    private final UserService userService;
    private Environment environment;

    @GetMapping("/users/status/check")
    public String status() {
        return "Working on port " + environment.getProperty("local.server.port");
    }

    @RequestMapping(value = "/users", params = {"pageSize", "page"}, method = RequestMethod.GET)
    @ResponseBody
    public PageData<UserResponse> getUsers(
        @RequestParam int pageSize,
        @RequestParam int page,
        @RequestParam(required = false) String textSearch,
        @RequestParam(required = false) String sortProperty,
        @RequestParam(required = false) String sortOrder
    ) throws HumanResourcesException {
        try {
            val currentUser = getCurrentUser();
            System.out.println(currentUser.toString());
            PageLink pageLink = createPageLink(pageSize, page, textSearch, sortProperty, sortOrder);
            val data = checkNotNull(userService.findAll(pageLink));
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            List<UserResponse> list = Collections.emptyList();
            if (data.getData() != null && !data.getData().isEmpty()) {
                list = new ArrayList<>();
                for (UserDto dto : data.getData()) {
                    if (dto != null) {
                        list.add(modelMapper.map(dto, UserResponse.class));
                    }
                }
            }
            return new PageData<>(list, data.getTotalPages(), data.getTotalElements(),
                data.hasNext());
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public UserDto findUserById(@PathVariable(USER_ID) Long userId) {
        return userService.findUserById(userId);
    }

    @RequestMapping(
        value = "/user",
        method = RequestMethod.POST,
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseBody
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userDetails) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        UserDto createdUser = userService.saveUser(userDto);
        UserResponse userResponse = modelMapper.map(createdUser, UserResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
}
