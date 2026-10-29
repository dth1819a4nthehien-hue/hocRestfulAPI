package vn.hoidanit.springrestwithai.controller;

import java.net.URI;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.springrestwithai.Model.User;
import vn.hoidanit.springrestwithai.Service.UserService;
import vn.hoidanit.springrestwithai.helper.ApiResponse;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> findAll() {
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách người dùng thành công", userService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> findById(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.notFound("Không tìm thấy người dùng có ID " + id));
        }
        return ResponseEntity.ok(ApiResponse.success("Lấy thông tin người dùng thành công", user));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<User>> save(@RequestBody User user) {
        try {
            User newUser = userService.save(user);
            return ResponseEntity.created(URI.create("/users/" + newUser.getId()))
                    .body(ApiResponse.created("Tạo người dùng thành công", newUser));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.conflict("Không thể tạo người dùng do dữ liệu trùng lặp hoặc vi phạm ràng buộc dữ liệu"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> update(@PathVariable("id") Long id, @RequestBody User user) {
        User updatedUser = userService.update(id, user);
        if (updatedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.notFound("Không tìm thấy người dùng có ID " + id));
        }
        return ResponseEntity.ok(ApiResponse.success("Cập nhật người dùng thành công", updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable("id") Long id) {
        if (!userService.deleteById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.notFound("Không tìm thấy người dùng có ID " + id));
        }
        return ResponseEntity.noContent().build();
    }
}
