package json;

import lombok.Data;
import lombok.experimental.Accessors;
import my.utils.plugin.json.JackonImpl;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class JackonImplTest {

    JackonImpl jackonBll = new JackonImpl();

    @Test
    public void toBean() {
        String jsonStr = "{\"userName\":'aaa',age:123,'sex':true,\"noField\":1}";
        Person person = jackonBll.toBean(jsonStr,Person.class);
        assertTrue(person.age==123);
        assertTrue(person.userName.equals("aaa"));
        assertTrue(person.sex==true);
    }

    @Test
    public void toList() {
        String jsonStr = "[{\"userName\":'aaa',age:123,'sex':true,\"noField\":1},{\"userName\":'bbb',age:345,'sex':false,\"noField\":2}]";
        List<Person> person = jackonBll.toList(jsonStr,Person.class);
        assertNotNull(person);
        Person personSingle = person.get(0);
        assertTrue(personSingle.age==123);
        assertTrue(personSingle.userName.equals("aaa"));
        assertTrue(personSingle.sex==true);
    }

    @Test
    public void toJson() {
        var person = new Person().setAge(30).setSex(false).setUserName("heqilin");
        var result = jackonBll.toJson(person);
        assertEquals("{\"userName\":\"heqilin\",\"age\":30,\"sex\":false}",result);

        var person1 = new Person().setAge(30).setSex(false).setUserName(null);
        var result1 = jackonBll.toJson(person1);
        assertEquals("{\"userName\":null,\"age\":30,\"sex\":false}",result1);
    }

    @Data
    @Accessors(chain = true)
    public  static class Person{
        private String userName;
        private int age;
        private boolean sex;
    }

}
