import { Component } from '@angular/core';
import {AsyncPipe, NgClass, NgForOf, NgIf} from "@angular/common";
import {NavigationEnd, Router, RouterLink, RouterOutlet} from "@angular/router";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {AsideComponent} from "../aside/aside.component";

@Component({
  selector: 'app-admin',
  standalone: true,
  templateUrl: './admin.component.html',
  imports: [
    // SaveUserComponent,
    NgIf,
    AsyncPipe,
    RouterLink,
    // AsideComponent,
    RouterOutlet,
    ReactiveFormsModule,
    NgClass,
    NgForOf,
    AsideComponent
  ],
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent {
  showAuthorsTable: boolean = false;
  showForm: boolean = false;
  formMode: 'add' | 'edit' = 'add';
  personForm: FormGroup;
  authors: Array<any> = [
    {
      name: 'Esthera Jackson',
      email: 'esthera@simmmple.com',
      role: 'Manager',
      department: 'Organization',
      status: 'Online',
      date: '14/06/21',
      avatarUrl: 'https://cdn.builder.io/api/v1/image/assets/TEMP/4829db975d17735dfcfe14231f294e18e08f0d42d75ac4f4671f64d25562c774?placeholderIfAbsent=true&apiKey=fbeb5a321590488ab4d866ae3e262c8d'
    },
  ];

  constructor(private router: Router, private fb: FormBuilder) {
    this.personForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.checkRoute(event.urlAfterRedirects);
      }
    });
  }

  checkRoute(url: string) {
    if (url.includes('authors')) {
      this.showAuthorsTable = true;
      this.showForm = true;
    } else if (url.includes('form')) {
      this.showAuthorsTable = false;
      this.showForm = false;
    } else {
      this.showAuthorsTable = true;
      this.showForm = false;
    }
  }

  editAuthor(author: any) {
    this.showForm = true;
    this.formMode = 'edit';
    this.personForm.patchValue({
      name: author.name,
      email: author.email,
      password: ''
    });
  }

  cancel() {
    this.showForm = false;
    this.personForm.reset();
  }

  onSubmit() {
    if (this.personForm.valid) {
      if (this.formMode === 'add') {
        this.addAuthor(this.personForm.value);
      } else {
        this.updateAuthor(this.personForm.value);
      }
      this.cancel();
    }
  }

  addAuthor(author: any) {
    console.log('Adding author:', author);
    this.authors.push(author);
  }

  updateAuthor(author: any) {
    console.log('Updating author:', author);

  }


}
