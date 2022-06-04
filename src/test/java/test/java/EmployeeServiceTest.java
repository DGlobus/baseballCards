package test.java;

import com.dasha.model.Employee;
import com.dasha.model.PostMap;
import com.dasha.service.EmployeeService.EmployeeService;
import com.dasha.service.EmployeeService.SearchParametrs;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class EmployeeServiceTest {

    private PostMap map = new PostMap();
    private Employee Gennadiy = Employee.builder()
                .firstName("Геннадий")
                .lastName("Кузьмин")
                .description("Lorem ipsum")
                .characteristics(List.of("honest", "introvert", "like criticism", "love of Learning", "pragmatism"))
                .post(map.getPosts().get(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707")))
                .build();

    private Employee Daria = Employee.builder()
                .firstName("Дарья")
                .lastName("Глобчастая")
                .description("Big Harry Potter Fan")
                .characteristics(List.of("introvert"))
                .post(map.getPosts().get(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5")))
                .build();

    private Employee Ivan = Employee.builder()
                .firstName("Иван")
                .lastName("Петров")
                .description("бла бла")
                .characteristics(List.of("бла", "бла бла бла"))
                .post(map.getPosts().get(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5")))
                .build();

    private Employee Petr = Employee.builder()
                .firstName("Петя")
                .lastName("Иванов")
                .description("алаллала")
                .characteristics(List.of("ла", "дада"))
                .post(map.getPosts().get(UUID.fromString("854ef89d-6c07-4665-936d-894d76a81707")))
                .build();

    private final List<Employee> allEmployees = List.of(Gennadiy, Daria, Ivan, Petr);

    @Test
    public void filterNameParametr(){
        //arrange
        List<Employee> expected = List.of(Ivan, Petr);

        SearchParametrs searchParametrs = new SearchParametrs();
        searchParametrs.setName("иван");

        //act
        EmployeeService employeeService = new EmployeeService();
        List<Employee> actual = employeeService.filter(allEmployees, searchParametrs);

        //assert
        assertEquals(expected, actual);
    }

    @Test
    public void filterIdParametr(){
        //arrange
        List<Employee> expected = List.of(Daria, Ivan);

        SearchParametrs searchParametrs = new SearchParametrs();
        searchParametrs.setId(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5"));

        //act
        EmployeeService employeeService = new EmployeeService();
        List<Employee> actual = employeeService.filter(allEmployees, searchParametrs);

        //assert
        assertEquals(expected, actual);
    }

    @Test
    public void sort(){
        //arrange
        List<Employee> expected = List.of(Daria, Petr, Gennadiy, Ivan);

        //act
        EmployeeService employeeService = new EmployeeService();
        List<Employee> actual = employeeService.sort(allEmployees);

        //assert
        assertEquals(expected, actual);
    }
}