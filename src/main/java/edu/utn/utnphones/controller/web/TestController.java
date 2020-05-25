package edu.utn.utnphones.controller.web;

import edu.utn.utnphones.controller.*;
import edu.utn.utnphones.domain.*;
import edu.utn.utnphones.exception.ResourcesNotExistException;
import edu.utn.utnphones.exception.UserAlreadyExistsException;
import edu.utn.utnphones.exception.UserNotexistException;
import edu.utn.utnphones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.Line;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    private SessionManager sessionManager;
    private ProvinceController provinceController;
    private CityController cityController;
    private RateController rateController;
    private CallController callController;
    private InvoiceController invoiceController;
    private LineTypeController lineTypeController;
    private PhoneLineController phoneLineController;
    private UserController userController;
    private UserTypeController userTypeController;

    @Autowired
    public TestController(SessionManager sessionManager, ProvinceController provinceController, CityController cityController, RateController rateController, CallController callController, InvoiceController invoiceController, LineTypeController lineTypeController, PhoneLineController phoneLineController, UserController userController, UserTypeController userTypeController) {
        this.sessionManager = sessionManager;
        this.provinceController = provinceController;
        this.cityController = cityController;
        this.rateController = rateController;
        this.callController = callController;
        this.invoiceController = invoiceController;
        this.lineTypeController = lineTypeController;
        this.phoneLineController = phoneLineController;
        this.userController = userController;
        this.userTypeController = userTypeController;
    }

    //MÉTODOS PROVINCE -----------------------------------------------------------------------------------------------------------------------
    @GetMapping("/province")
    public ResponseEntity<List<Province>> getAllProvinces(@RequestHeader("Authorization") String sessionToken){
        return ResponseEntity.ok().body(provinceController.getAllProvinces());
    }

    @GetMapping("/province/{id}")
    public ResponseEntity<Province> getByIdProvince(@RequestHeader("Authorization") String sessionToken, @PathVariable int id) throws ResourcesNotExistException {
        return ResponseEntity.ok().body(provinceController.getById(id));
    }

    @PostMapping("/province")
    public ResponseEntity addProvince(@RequestHeader("Authorization") String sessionToken, @RequestBody @Valid Province p){
        return ResponseEntity.status(HttpStatus.CREATED).body(provinceController.addProvince(p));
    }

    //MÉTODOS CITY -----------------------------------------------------------------------------------------------------------------------
    @GetMapping("/city")
    public ResponseEntity<List<City>> getAllCities(@RequestHeader("Authorization") String sessionToken){
        return ResponseEntity.ok().body(cityController.getAllCities());
    }

    @GetMapping("/city/{id}")
    public ResponseEntity<City> getByIdCity(@RequestHeader("Authorization") String sessionToken, @PathVariable int id) throws ResourcesNotExistException {
        return ResponseEntity.ok().body(cityController.getById(id));
    }

    @PostMapping("/city")
    public ResponseEntity addCity(@RequestHeader("Authorization") String sessionToken, @RequestBody @Valid City city){
        return ResponseEntity.status(HttpStatus.CREATED).body(cityController.addCity(city));
    }

    //MÉTODOS RATE -----------------------------------------------------------------------------------------------------------------------
    @GetMapping("/rate")
    public ResponseEntity<List<Rate>> getAllRates(@RequestHeader("Authorization") String sessionToken){
        return ResponseEntity.ok().body(rateController.getAllRates());
    }

    @GetMapping("/rate/{idFrom}")
    public ResponseEntity<List<Rate>> getByIdFromRate(@RequestHeader("Authorization") String sessionToken, @PathVariable int idFrom) throws ResourcesNotExistException {
        return ResponseEntity.ok().body(rateController.getByIdFrom(idFrom));
    }

    @GetMapping("/rate/{idTo}")
    public ResponseEntity<List<Rate>> getByIdToRate(@RequestHeader("Authorization") String sessionToken, @PathVariable int idTo) throws ResourcesNotExistException {
        return ResponseEntity.ok().body(rateController.getByIdTo(idTo));
    }

    @PostMapping("/rate")
    public ResponseEntity addRate(@RequestHeader("Authorization") String sessionToken, @RequestBody @Valid Rate rate){
        return ResponseEntity.status(HttpStatus.CREATED).body(rateController.addRate(rate));
    }

    //MÉTODOS CALL -----------------------------------------------------------------------------------------------------------------------
    @GetMapping("/call")
    public ResponseEntity<List<Call>> getAllCalls(@RequestHeader("Authorization") String sessionToken){
        return ResponseEntity.ok().body(callController.getAllCalls());
    }

    @GetMapping("/call/{id}")
    public ResponseEntity<Call> getByIdCall(@RequestHeader("Authorization") String sessionToken, @PathVariable int id) throws ResourcesNotExistException {
        return ResponseEntity.ok().body(callController.getById(id));
    }

    @PostMapping("/call")
    public ResponseEntity addCall(@RequestHeader("Authorization") String sessionToken, @RequestBody @Valid Call call){
        return ResponseEntity.status(HttpStatus.CREATED).body(callController.addCall(call));
    }

    //MÉTODOS INVOICE -----------------------------------------------------------------------------------------------------------------------
    @GetMapping("/invoice")
    public ResponseEntity<List<Invoice>> getAllInvoices(@RequestHeader("Authorization") String sessionToken){
        return ResponseEntity.ok().body(invoiceController.getAllInvoice());
    }

    @GetMapping("/invoice/{id}")
    public ResponseEntity<Invoice> getByIdInvoice(@RequestHeader("Authorization") String sessionToken, @PathVariable int id) throws ResourcesNotExistException {
        return ResponseEntity.ok().body(invoiceController.getById(id));
    }

    @PostMapping("/Invoice")
    public ResponseEntity addInvoice(@RequestHeader("Authorization") String sessionToken, @RequestBody @Valid Invoice invoice){
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceController.addInvoice(invoice));
    }
    //MÉTODOS LINETYPE -----------------------------------------------------------------------------------------------------------------------
    @GetMapping("/linetype")
    public ResponseEntity<List<LineType>> getAllLineTypes(@RequestHeader("Authorization") String sessionToken){
        return ResponseEntity.ok().body(lineTypeController.getAllLineTypes());
    }

    @GetMapping("/linetype/{id}")
    public ResponseEntity<LineType> getByIdLineType(@RequestHeader("Authorization") String sessionToken, @PathVariable int id) throws ResourcesNotExistException {
        return ResponseEntity.ok().body(lineTypeController.getById(id));
    }

    @PostMapping("/linetype")
    public ResponseEntity addLineType(@RequestHeader("Authorization") String sessionToken, @RequestBody @Valid LineType lineType){
        return ResponseEntity.status(HttpStatus.CREATED).body(lineTypeController.addLineType(lineType));
    }

    //MÉTODOS PHONELINE -----------------------------------------------------------------------------------------------------------------------
    @GetMapping("/phoneline")
    public ResponseEntity<List<PhoneLine>> getAllPhoneLines(@RequestHeader("Authorization") String sessionToken){
        return ResponseEntity.ok().body(phoneLineController.getAllLinePhones());
    }

    @GetMapping("/phoneline/{id}")
    public ResponseEntity<PhoneLine> getByIdPhoneLine(@RequestHeader("Authorization") String sessionToken, @PathVariable int id) throws ResourcesNotExistException {
        return ResponseEntity.ok().body(phoneLineController.getById(id));
    }

    @PostMapping("/phoneline")
    public ResponseEntity addPhoneLine(@RequestHeader("Authorization") String sessionToken, @RequestBody @Valid PhoneLine phoneLine){
        return ResponseEntity.status(HttpStatus.CREATED).body(phoneLineController.addLinePhone(phoneLine));
    }

    //MÉTODOS USERTYPE -----------------------------------------------------------------------------------------------------------------------
    @GetMapping("/usertype")
    public ResponseEntity<List<UserType>> getAllUserType(@RequestHeader("Authorization") String sessionToken){
        return ResponseEntity.ok().body(userTypeController.getAllUserType());
    }

    @GetMapping("/usertype/{id}")
    public ResponseEntity<UserType> getByIdUserType(@RequestHeader("Authorization") String sessionToken, @PathVariable int id) throws ResourcesNotExistException {
        return ResponseEntity.ok().body(userTypeController.getById(id));
    }

    @PostMapping("/usertype")
    public ResponseEntity addUserType(@RequestHeader("Authorization") String sessionToken, @RequestBody @Valid UserType userType){
        return ResponseEntity.status(HttpStatus.CREATED).body(userTypeController.addUserType(userType));
    }

    //MÉTODOS USER -----------------------------------------------------------------------------------------------------------------------
    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUser(@RequestHeader("Authorization") String sessionToken){
        return ResponseEntity.ok().body(userController.getAllUser());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getByIdUser(@RequestHeader("Authorization") String sessionToken, @PathVariable int id) throws UserNotexistException {
        return ResponseEntity.ok().body(userController.getUserById(id));
    }

    @PostMapping("/user/add")
    public ResponseEntity addUser(@RequestHeader("Authorization") String sessionToken, @RequestBody @Valid User user) throws UserAlreadyExistsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userController.createUser(user));
    }

    @PostMapping("/user/udp")
    public ResponseEntity udpUser(@RequestHeader("Authorization") String sessionToken, @RequestBody @Valid User user) throws UserNotexistException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userController.updateUser(user));
    }

}
