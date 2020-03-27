package org.example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ParserTest {
    public List<Item> parslog = new ArrayList<>();

    @Test
    public void test() {
        Config config = new Config();
        new Parser(config).parserLog();
        //System.out.println(parslog.toString());
        assert(true);
    }
}
