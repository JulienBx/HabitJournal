package com.julienbx.habitjournal.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.julienbx.habitjournal.model.Entry;
import com.julienbx.habitjournal.service.EntryService;
import com.julienbx.habitjournal.util.CustomErrorType;

@RestController
@RequestMapping("/calendar")
public class CalendarController {

	public static final Logger logger = LoggerFactory.getLogger(CalendarController.class);

	@Autowired
	EntryService entryService; 

	// -------------------Retrieve All Entries from calendar---------------------------------------------

	@RequestMapping(value = "/entries/", method = RequestMethod.GET)
	public ResponseEntity<List<Entry>> listAllEntries() {
		List<Entry> entries = entryService.findAllEntries();
		if (entries.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Entry>>(entries, HttpStatus.OK);
	}

	// -------------------Retrieve Single Entry------------------------------------------

	@RequestMapping(value = "/entry/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getEntry(@PathVariable("id") long id) {
		logger.info("Fetching Entry with id {}", id);
		Entry entry = entryService.findById(id);
		if (entry == null) {
			logger.error("Entry with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Entry with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Entry>(entry, HttpStatus.OK);
	}

	// -------------------Create a Entry-------------------------------------------

	@RequestMapping(value = "/entry/", method = RequestMethod.POST)
	public ResponseEntity<?> createEntry(@RequestBody Entry entry, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Entry : {}", entry);

		if (entryService.isEntryExist(entry)) {
			logger.error("Unable to create. A Entry with name {} already exist", entry.getLabel());
			return new ResponseEntity(new CustomErrorType("Unable to create. A Entry with name " + 
			entry.getLabel() + " already exist."),HttpStatus.CONFLICT);
		}
		entryService.saveEntry(entry);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/entry/{id}").buildAndExpand(entry.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Entry ------------------------------------------------

	@RequestMapping(value = "/entry/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateEntry(@PathVariable("id") long id, @RequestBody Entry entry) {
		logger.info("Updating Entry with id {}", id);

		Entry currentEntry = entryService.findById(id);

		if (currentEntry == null) {
			logger.error("Unable to update. Entry with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. Entry with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentEntry.setId(entry.getId());
		currentEntry.setLabel(entry.getLabel());
		currentEntry.setDate(entry.getDate());
		

		entryService.updateEntry(currentEntry);
		return new ResponseEntity<Entry>(currentEntry, HttpStatus.OK);
	}

	// ------------------- Delete a Entry-----------------------------------------

	@RequestMapping(value = "/entry/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteEntry(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Entry with id {}", id);

		Entry entry = entryService.findById(id);
		if (entry == null) {
			logger.error("Unable to delete. Entry with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Entry with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		entryService.deleteEntryById(id);
		return new ResponseEntity<Entry>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Entries-----------------------------

	@RequestMapping(value = "/entry/", method = RequestMethod.DELETE)
	public ResponseEntity<Entry> deleteAllEntries() {
		logger.info("Deleting All Entries");

		entryService.deleteAllEntries();
		return new ResponseEntity<Entry>(HttpStatus.NO_CONTENT);
	}

}