package pro.sky.homework2_14.IntListPart3HW16.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.homework2_14.IntListPart3HW16.interfaces.IntegerList;

@RestController
public class IntListController {
    private final IntegerList integerList;

    public IntListController(IntegerList integerList) {
        this.integerList = integerList;
    }

    @GetMapping(path = "/getAll")
    public Integer[] getAllElements() {
        return integerList.getAll();
    }

    @GetMapping(path = "/add")
    public Integer addElement(@RequestParam("number") Integer str) {
        return integerList.add(str);
    }

    @GetMapping(path = "/addindex")
    public Integer addElementByIndex(@RequestParam("id") int index,
                                     @RequestParam("number") Integer str) {
        return integerList.add(index, str);
    }

    @GetMapping(path = "/set")
    public Integer setElement(@RequestParam("id") int index,
                              @RequestParam("number") Integer str) {
        return integerList.set(index, str);
    }


    @GetMapping(path = "/removeid")
    public Integer removeElementById(@RequestParam("id") int index) {
        return integerList.removeId(index);
    }

    @GetMapping(path = "/remove")
    public Integer removeElement(@RequestParam("number") Integer str) {
        return integerList.remove(str);
    }

    @GetMapping(path = "/contains")
    public boolean isContainsElement(@RequestParam("number") Integer str) {
        return integerList.contains(str);
    }

    @GetMapping(path = "/indexof")
    public int whatIndexOfElement(@RequestParam("number") Integer str) {
        return integerList.indexOf(str);
    }

    @GetMapping(path = "/lastindex")
    public int whatLastIndexOfElement(@RequestParam("number") Integer str) {
        return integerList.lastIndexOf(str);
    }

    @GetMapping(path = "/get")
    public Integer getElementById(@RequestParam("id") int index) {
        return integerList.get(index);
    }

    @GetMapping(path = "/equals")
    public boolean isListsEquals(@RequestParam("list") IntegerList otherList) {
        return integerList.equals(otherList);
    }

    @GetMapping(path = "/size")
    public int sizeOfList() {
        return integerList.size();
    }

    @GetMapping(path = "/empty")
    public boolean isEmptyOfList() {
        return integerList.isEmpty();
    }

    @GetMapping(path = "/clear")
    public void clearList() {
        integerList.clear();
    }

    @GetMapping(path = "/new")
    public Integer[] newArray() {
        return integerList.toArray();
    }

}
