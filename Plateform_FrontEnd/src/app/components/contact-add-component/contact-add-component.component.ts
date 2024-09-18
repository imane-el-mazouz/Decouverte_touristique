import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ContactDto } from '../../dto/contactDTO/contact.dto.interface';
import { ContactService } from '../../service/contact-service/contact-service.service';
import { FormsModule } from '@angular/forms';
import {NavBarComponent} from "../shared/nav-bar/nav-bar.component";
import {FooterComponent} from "../shared/footer/footer.component";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-contact-add',
  templateUrl: './contact-add-component.component.html',
  standalone: true,
  styleUrls: ['./contact-add-component.component.css'],
  imports: [FormsModule, NavBarComponent, FooterComponent, NgIf]
})
export class ContactAddComponent {
  contact: ContactDto = { id: 0, firstName: '', fullName: '', email: '', message: '' };
  successMessage: string | null = null;
  constructor(private contactService: ContactService, private router: Router) { }

  addContact(): void {
    this.contactService.createContact(this.contact).subscribe(() => {
      this.successMessage = 'Your contact was successful. Thank you!';
      this.router.navigate(['/addContact']);
    });
  }
}
