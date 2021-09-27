package com.example.recordstoreapi.controller;

import com.example.recordstoreapi.model.Record;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by bonallure on 9/14/21
 */

@RestController
public class RecordStoreController {

    // Mimicking unique ID's
    private static int idCounter = 1;

    // Mimicking database
    private List<Record> recordList;

    private RecordStoreController(){
        recordList = new ArrayList<>();

        recordList.add(new Record("The Beach Boys", "Pet Sounds", "1968", idCounter++));
        recordList.add(new Record("Billy Joel", "The Stranger", "1977", idCounter++));
        recordList.add(new Record("The Beatles", "Revolver", "1964", idCounter++));
        recordList.add(new Record("Kanye West", "My Beautiful Dark Twisted Fantasy", "2008", idCounter++));
        recordList.add(new Record("Sturgill Simpson", "Metamodern Sounds in Country Music", "2010", idCounter++));
    }

    // POST localhost:8080/records
    @RequestMapping(value = "/records", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Record createRecord(@RequestBody @Valid Record record){

        // setting the id
        record.setId(idCounter++);
        recordList.add(record);

        return record;
    }

    // GET localhost:8080/records
    @RequestMapping(value = "/records", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public List<Record> getRecords(@RequestParam(required = false) String artist){

        List<Record> returnList = new ArrayList<>();

        if (artist != null){
            for (Record record: recordList){
                if (record.getArtist().contains(artist))
                    returnList.add(record);
            }
        }
        else
            returnList = recordList;

        return returnList;
    }

    // GET localhost:8080/records/{id}
    @RequestMapping(value = "/records/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public Record getRecordById(@PathVariable int id){

        Record returnRecord = null;

        for (Record record : recordList){
            if (record.getId() == id){
                returnRecord = record;
                break;
            }
        }

        return returnRecord;
    }

    // PUT /records/{id}
    @RequestMapping(value = "/records/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateRecordById(@PathVariable int id, @RequestBody Record record) {
        int index = -1;

        for(int i = 0; i < recordList.size(); i++) {
            if(recordList.get(i).getId() == id) {
                index = i;
                break;
            }
        }

        if (index >= 0) {
            recordList.set(index, record);
        }
    }

    @RequestMapping(value = "/records/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteRecordById(@PathVariable int id) {
        int index = -1;

        for(int i = 0; i < recordList.size(); i++) {
            if(recordList.get(i).getId() == id) {
                index = i;
                break;
            }
        }

        if (index >= 0) {
            recordList.remove(index);
        }
    }
}
