import { Component } from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {ContactDto} from "../../dto/contactDTO/contact.dto.interface";
import {ContactService} from "../../service/contact-service/contact-service.service";
import {Router} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {TableModule} from "primeng/table";
import {DialogModule} from "primeng/dialog";

@Component({
  selector: 'app-contact-page',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    FormsModule,
    TableModule,
    DialogModule
  ],
  templateUrl: './contact-page.component.html',
  styleUrl: './contact-page.component.scss'
})
export class ContactPageComponent {
  contacts: ContactDto[] = [];
  selectedContact: ContactDto | null = null;
  loading: number = 0;
  totalRecords: number | undefined;
  visible: boolean  = false ;

  constructor(private contactService: ContactService) { }

  ngOnInit(): void {
    this.loadContacts();
  }

  loadContacts(): void {
    this.contactService.getAllContacts().subscribe(
      (data: ContactDto[]) => {
        this.contacts = data;
      },
      (error) => {
        console.error('Error loading contacts', error);
      }
    );
  }

  deleteContact(id: number | undefined): void {
    this.contactService.deleteContact(id).subscribe(() => {
      this.contacts = this.contacts.filter(contact => contact.id !== id);
    });
  }

  selectContactForUpdate(contact: ContactDto): void {
    this.selectedContact = { ...contact };
  }

  updateContact(): void {
    if (this.selectedContact) {
      this.contactService.updateContact(this.selectedContact.id, this.selectedContact).subscribe(() => {
        this.loadContacts();
        this.selectedContact = null;
      });
    }
  }

  cancelUpdate(): void {
    this.selectedContact = null;
  }

  viewDialog(){
    this.visible= true;
  }
}
