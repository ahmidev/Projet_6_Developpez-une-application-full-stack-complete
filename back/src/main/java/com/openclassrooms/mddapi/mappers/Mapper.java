package com.openclassrooms.mddapi.mappers;


import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.dtos.UserLoginDTO;
import com.openclassrooms.mddapi.dtos.UserRegisterDTO;
import com.openclassrooms.mddapi.models.User;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class Mapper {

//    public RentalDTO toRentalDTO(Rental rental) {
//        RentalDTO rentalDTO = new RentalDTO();
//        rentalDTO.setId(rental.getId());
//        rentalDTO.setName(rental.getName());
//        rentalDTO.setSurface(rental.getSurface());
//        rentalDTO.setPrice(rental.getPrice());
//        rentalDTO.setPicture(rental.getPicture());
//        rentalDTO.setDescription(rental.getDescription());
//        rentalDTO.setCreatedAt(rental.getCreatedAt());
//        rentalDTO.setUpdatedAt(rental.getUpdatedAt());
//        rentalDTO.setUserId(rental.getUser().getId());
//        return rentalDTO;
//    }
//
//    public Rental toRentalEntity(RentalDTO rentalDTO, User user) {
//        Rental rental = new Rental();
//        rental.setName(rentalDTO.getName());
//        rental.setSurface(rentalDTO.getSurface());
//        rental.setPrice(rentalDTO.getPrice());
//        rental.setPicture(rentalDTO.getPicture());
//        rental.setDescription(rentalDTO.getDescription());
//        rental.setCreatedAt(rentalDTO.getCreatedAt());
//        rental.setUpdatedAt(rentalDTO.getUpdatedAt());
//        rental.setUser(user);
//        return rental;
//    }
//
//    public Rental toRentalEntity(UpdateRentalDto updateRentalDto, Rental rental) {
//        rental.setName(updateRentalDto.getName());
//        rental.setSurface(updateRentalDto.getSurface());
//        rental.setPrice(updateRentalDto.getPrice());
//        rental.setDescription(updateRentalDto.getDescription());
//        return rental;
//    }
//
//    public MessageDTO toMessageDTO(Message message) {
//        MessageDTO messageDTO = new MessageDTO();
//        messageDTO.setId(message.getId());
//        messageDTO.setRentalId(message.getRental().getId());
//        messageDTO.setUserId(message.getUser().getId());
//        messageDTO.setMessage(message.getMessage());
//        messageDTO.setCreatedAt(message.getCreatedAt());
//        messageDTO.setUpdatedAt(message.getUpdatedAt());
//        return messageDTO;
//    }
//
//    public Message toMessageEntity(MessageDTO messageDTO, User user, Rental rental) {
//        Message message = new Message();
//        message.setId(messageDTO.getId());
//        message.setMessage(messageDTO.getMessage());
//        message.setCreatedAt(messageDTO.getCreatedAt());
//        message.setUpdatedAt(messageDTO.getUpdatedAt());
//        message.setUser(user);
//        message.setRental(rental);
//        return message;
//    }
//
//    public SendMessageDTO toSendMessageDTO(Message message) {
//        SendMessageDTO sendMessageDTO = new SendMessageDTO();
//        sendMessageDTO.setRentalId(message.getRental().getId());
//        sendMessageDTO.setUserId(message.getUser().getId());
//        sendMessageDTO.setMessage(message.getMessage());
//        sendMessageDTO.setCreatedAt(message.getCreatedAt());
//        sendMessageDTO.setUpdatedAt(message.getUpdatedAt());
//        return sendMessageDTO;
//    }
//
//    public Message toMessageEntity(SendMessageDTO sendMessageDTO, User user, Rental rental) {
//        Message message = new Message();
//        message.setMessage(sendMessageDTO.getMessage());
//        message.setUser(user);
//        message.setRental(rental);
//        message.setCreatedAt(sendMessageDTO.getCreatedAt());
//        message.setUpdatedAt(sendMessageDTO.getUpdatedAt());
//        return message;
//    }

    public UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        return userDTO;
    }

    public User toUserEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setCreatedAt(userDTO.getCreatedAt());
        user.setUpdatedAt(userDTO.getUpdatedAt());
        return user;
    }

    public User toUserEntity(UserRegisterDTO userRegisterDTO) {
        User user = new User();
        user.setEmail(userRegisterDTO.getEmail());
        user.setName(userRegisterDTO.getUsername());
        user.setPassword(userRegisterDTO.getPassword());
        return user;
    }

    public UserLoginDTO toUserLoginDTO(User user) {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail(user.getEmail());
        userLoginDTO.setPassword(user.getPassword());
        return userLoginDTO;
    }

//    public Rental toRentalEntity(CreateRentalDto createRentalDto, User user, String pictureUrl) {
//        Rental rental = new Rental();
//        rental.setName(createRentalDto.getName());
//        rental.setSurface(createRentalDto.getSurface());
//        rental.setPrice(createRentalDto.getPrice());
//        rental.setPicture(pictureUrl.replace("\\", "/"));
//        rental.setDescription(createRentalDto.getDescription());
//        rental.setUser(user);
//        rental.setCreatedAt(Instant.now());
//        rental.setUpdatedAt(Instant.now());
//        return rental;
//    }
}