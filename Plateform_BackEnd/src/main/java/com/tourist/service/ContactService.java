package com.tourist.service;

import com.tourist.dto.ContactDTO;
import com.tourist.mapper.ContactMapper;
import com.tourist.model.Contact;
import com.tourist.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactMapper contactMapper;

    public List<ContactDTO> getAllContacts() {
        return contactRepository.findAll().stream()
                .map(contactMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ContactDTO> getContactById(Long id) {
        return contactRepository.findById(id)
                .map(contactMapper::toDto);
    }

    public ContactDTO saveContact(ContactDTO contactDTO) {
        Contact contact = contactMapper.toEntity(contactDTO);
        Contact savedContact = contactRepository.save(contact);
        return contactMapper.toDto(savedContact);
    }

    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }

    public ContactDTO updateContact(Long id, ContactDTO contactDTO) {
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
        contactMapper.partialUpdate(contactDTO, existingContact);
        Contact updatedContact = contactRepository.save(existingContact);
        return contactMapper.toDto(updatedContact);
    }
}
