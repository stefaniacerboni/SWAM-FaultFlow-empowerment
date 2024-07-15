import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../core/services/user.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {
  user: any = {
    username: '',
    name: '',
    surname: ''
  };
  currentPassword: string = '';
  newPassword: string = '';
  confirmNewPassword: string = '';
  passwordsDoNotMatch: boolean = false;

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.loadUserProfile();
  }

  loadUserProfile(): void {
    this.userService.getCurrentUser().subscribe(
      user => {
        this.user = user;
      },
      error => {
        console.error('Error loading user profile', error);
      }
    );
  }

  onSubmit(): void {
    if (this.newPassword !== this.confirmNewPassword) {
      this.passwordsDoNotMatch = true;
      return;
    }

    const updatedUser = {
      username: this.user.username,
      name: this.user.name,
      surname: this.user.surname,
      currentPassword: this.currentPassword,
      newPassword: this.newPassword ? this.newPassword : null
    };

    this.userService.updateUserProfile(updatedUser).subscribe(
      (response) => {
        alert('Profile updated successfully');
        this.router.navigate(['/home']);
      },
      (error) => {
        console.error('Error updating profile', error);
      }
    );
  }
}
