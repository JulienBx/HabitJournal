package com.julienbx.habitjournal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.julienbx.habitjournal.model.Entry;
import com.julienbx.habitjournal.repositories.EntryRepository;


@Service("entryService")
@Transactional
public class EntryServiceImpl implements EntryService{

	@Autowired
	private EntryRepository entryRepository;

	public Entry findById(Long id) {
		return entryRepository.findOne(id);
	}

	public Entry findByName(String label) {
		return entryRepository.findByLabel(label);
	}

	public void saveEntry(Entry entry) {
		entryRepository.save(entry);
	}

	public void updateEntry(Entry entry){
		saveEntry(entry);
	}

	public void deleteEntryById(Long id){
		entryRepository.delete(id);
	}

	public void deleteAllEntries(){
		entryRepository.deleteAll();
	}

	public List<Entry> findAllEntries(){
		return entryRepository.findAll();
	}

	public boolean isEntryExist(Entry entry) {
		return findByName(entry.getLabel()) != null;
	}

}
