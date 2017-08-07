package com.julienbx.habitjournal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.julienbx.habitjournal.model.Entry;
import com.julienbx.habitjournal.model.User;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

    Entry findByLabel(String label);

}
