import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from './layout/navbar/navbar.component';
import { User } from './models/user.model';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'Admin';



  public async ngOnInit() {
    
  }


}
