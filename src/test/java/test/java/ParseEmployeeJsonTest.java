package test.java;

import com.dasha.model.Employee;
import com.dasha.model.PostMap;
import com.dasha.service.getCards.ParseEmployeeJson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class ParseEmployeeJsonTest {

    PostMap map = new PostMap();
    private final List<Employee> employees = new ArrayList<>();
    {
        employees.add(Employee.builder()
                .firstName("Геннадий")
                .lastName("Кузьмин")
                .description("Lorem ipsum")
                .characteristics(List.of("honest", "introvert", "like criticism", "love of Learning", "pragmatism"))
                .post(map.getPosts().get(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707")))
                .build());

        employees.add(Employee.builder()
                .firstName("Дарья")
                .lastName("Глобчастая")
                .description("Big Harry Potter Fan")
                .characteristics(List.of("introvert"))
                .post(map.getPosts().get(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5")))
                .build());

        employees.add(Employee.builder()
                .firstName("Иван")
                .lastName("Петров")
                .description("бла бла")
                .characteristics(List.of("бла", "бла бла бла"))
                .post(map.getPosts().get(UUID.fromString("a3dec21f-1187-4b05-896b-96b580b453a5")))
                .build());

        employees.add(Employee.builder()
                .firstName("Петя")
                .lastName("Иванов")
                .description("алаллала")
                .characteristics(List.of("ла", "дада"))
                .post(map.getPosts().get(UUID.fromString("854ef89d-6c07-4665-936d-894d76a81707")))
                .build());
    }


    @Test
    public void readTest(){
        //arrange
        //act
        List<Employee> employeeFromMethod = new ParseEmployeeJson().read(ParseEmployeeJsonTest.class.getClassLoader().getResource("cards.json").getPath());
        //assert
        assertEquals(employees, employeeFromMethod);
    }
}