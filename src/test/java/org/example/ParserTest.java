package org.example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParserTest {
    public List<Item> parslog = new ArrayList<>();

    @Test
    public void test() {
        new Parser().parserLog();
        //System.out.println(parslog.toString());
        assert(true);
    }

    @Test
    public void test1() {
        System.out.println(new LogQuery().allRecords());
        assert(true);
    }

    @Test
    public void test2() {
        System.out.println(new LogQuery().returnRecordQuonty("login"));
        assert(true);
    }

    @Test
    public void test3() {
        System.out.println(Arrays.toString(new LogQuery().returnRecordMax("ipAdress")));
        assert(true);
    }
}
