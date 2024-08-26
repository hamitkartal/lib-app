package com.library.library_app.service;

import com.library.library_app.dto.ReqBodies.UserControllerReqBodies.UserIdAndBookIdDTO;
import com.library.library_app.dto.ReqBodies.UserControllerReqBodies.UserIdDTO;
import com.library.library_app.dto.ServiceResponse;
import com.library.library_app.model.RentedBook;
import com.library.library_app.model.ReturnedBook;
import com.library.library_app.model.User;
import com.library.library_app.repository.BookRepository;
import com.library.library_app.repository.RentedBookRepository;
import com.library.library_app.repository.ReturnedBookRepository;
import com.library.library_app.repository.UserRepository;
import com.library.library_app.config.RentConfig;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentedBookRepository rentedBookRepository;

    @Autowired
    private ReturnedBookRepository returnedBookRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public ServiceResponse getAllUsers() {
        try {
            List<User> users = userRepository.getAllUsers();
            return new ServiceResponse(HttpStatus.OK, "Content successfully retrieved.", users);
        }
        catch (Exception e) {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving users.", e);
        }
    }

    @Override
    public ServiceResponse getUserById(UserIdDTO userId) {
        try {
            Long id = userId.id();
            Optional<User> optionalUser = userRepository.getUserById(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                return new ServiceResponse(HttpStatus.OK, "Content successfully retrieved.", user);
            }
            else {
                return new ServiceResponse(HttpStatus.NOT_FOUND, "User not found.", null);
            }
        }
        catch (Exception e) {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving users.", e);
        }
    }

    @Override
    public ServiceResponse createUser(User user) {
        try {
            userRepository.save(user);
            return new ServiceResponse(HttpStatus.CREATED, "User created successfully.", user);
        }
        catch (Exception e) {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating user.", e);
        }
    }

    @Override
    public ServiceResponse updateUserById(User user) {
        try {
            userRepository.save(user);
            return new ServiceResponse(HttpStatus.OK, "User updated successfully.", user);
        }
        catch (Exception e) {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating user.", e);
        }
    }

    @Override
    public ServiceResponse deleteUserById(UserIdDTO userId) {
        Long id = userId.id();

        if (userRepository.getUserById(id).isEmpty()) {
            return new ServiceResponse(HttpStatus.NOT_FOUND, "User not found.", null);
        }

        if (!rentedBookRepository.getAllRentedBooksByUserId(id).isEmpty()) {
            return new ServiceResponse(HttpStatus.PRECONDITION_REQUIRED, "Rented books cannot be deleted.", null);
        }

        try {
            userRepository.deleteById(id);
            return new ServiceResponse(HttpStatus.OK, "User deleted successfully.", userId);
        }
        catch (Exception e) {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting user.", e);
        }
    }

    @Transactional
    @Override
    public ServiceResponse rentBookById(UserIdAndBookIdDTO userIdAndBookIdDTO) {
        Long userId = userIdAndBookIdDTO.userId();
        Long bookId = userIdAndBookIdDTO.bookId();

        try {
            if (!userRepository.existsById(userId)) {
                return new ServiceResponse(HttpStatus.NOT_FOUND, "User not found.", null);
            }

            if (!bookRepository.existsById(bookId)) {
                return new ServiceResponse(HttpStatus.NOT_FOUND, "Book not found.", null);
            }

            if (rentedBookRepository.existsByUserIdAndBookId(userId, bookId)) {
                return new ServiceResponse(HttpStatus.CONFLICT, "Book already rented.", null);
            }

            if (!bookRepository.existsInStockById(bookId)) {
                return new ServiceResponse(HttpStatus.NOT_FOUND, "Book is not available.", null);
            }

            RentedBook rentedBook = new RentedBook(userId, bookId, LocalDateTime.now(), LocalDateTime.now().plusDays(RentConfig.MAX_DAYS_FOR_RENT));
            rentedBookRepository.save(rentedBook);
            bookRepository.decreaseBookStockByOneByBookId(bookId);
            return new ServiceResponse(HttpStatus.OK, "Rent successfully completed.", rentedBook);
        } catch (Exception e) {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error rent book.", e);
        }
    }

    @Transactional
    @Override
    public ServiceResponse returnBookById(UserIdAndBookIdDTO userIdAndBookId) {
        Long userId = userIdAndBookId.userId();
        Long bookId = userIdAndBookId.bookId();

        try {
            if (!userRepository.existsById(userId)) {
                return new ServiceResponse(HttpStatus.NOT_FOUND, "User not found.", null);
            }

            if (!bookRepository.existsById(bookId)) {
                return new ServiceResponse(HttpStatus.NOT_FOUND, "Book not found.", null);
            }

            if (!rentedBookRepository.existsByUserIdAndBookId(userId, bookId)) {
                return new ServiceResponse(HttpStatus.NOT_FOUND, "Book is not rented before.", null);
            }

            RentedBook rentedBook = rentedBookRepository.getRentedBookByUserIdAndBookId(userId, bookId);

            ReturnedBook returnedBook = new ReturnedBook(userId, bookId, rentedBook.getStartDate(), rentedBook.getDueDate(), LocalDateTime.now());

            returnedBookRepository.save(returnedBook);
            rentedBookRepository.delete(rentedBook);
            bookRepository.increaseBookStockByOneByBookId(bookId);
            return new ServiceResponse(HttpStatus.OK, "Rent successfully completed.", rentedBook);
        }
        catch (Exception e) {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error returning book.", e);
        }
    }
}
