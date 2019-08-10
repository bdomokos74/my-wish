import {Component, OnInit} from '@angular/core';

declare const gapi: any;

@Component({
  selector: 'app-user-info-dialog',
  templateUrl: './user-info-dialog.component.html',
  styleUrls: ['./user-info-dialog.component.css']
})
export class UserInfoDialogComponent implements OnInit {

  constructor() {
  }

  ngOnInit() {
  }

  public auth2: any;

  public googleInit() {
    gapi.load('auth2', () => {

      this.auth2 = gapi.auth2.init({
        // @ts-ignore
        client_id: GOOGLE_CLIENT_ID,
        cookiepolicy: 'single_host_origin',
        scope: 'profile email'
      });
      this.attachSignin(document.getElementById('googleBtn'));
    });
  }

  public attachSignin(element) {
    this.auth2.attachClickHandler(element, {},
      (googleUser) => {
        var profile = googleUser.getBasicProfile();
        console.log('id_token: ' + googleUser.getAuthResponse().id_token);
        console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
        console.log('Name: ' + profile.getName());
        console.log('Image URL: ' + profile.getImageUrl());
        console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
      }, (error) => {
        alert(JSON.stringify(error, undefined, 2));
      });

  }

  ngAfterViewInit() {
    this.googleInit();
  }
}
