package com.julienbx.habitjournal.service;


import java.util.List;

import com.julienbx.habitjournal.model.Entry;

public interface EntryService {
	
	Entry findById(Long id);

	Entry findByName(String name);

	void saveEntry(Entry entry);

	void updateEntry(Entry entry);

	void deleteEntryById(Long id);

	void deleteAllEntries();

	List<Entry> findAllEntries();

	boolean isEntryExist(Entry entry);
}