import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutComponent } from './shared/layout/layout.component';
import { HomeComponent } from './features/home/home.component';
import { SystemsComponent } from './features/systems/systems.component';
import { ImportComponent } from './features/import/import.component';
import { EditProfileComponent } from './features/edit-profile/edit-profile.component';
import { LoginComponent } from './features/login/login.component';
import { AuthGuard } from './core/services/auth-guard.service';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: '',
    component: LayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'home', component: HomeComponent },
      { path: 'systems', component: SystemsComponent },
      { path: 'import', component: ImportComponent },
      { path: 'edit-profile', component: EditProfileComponent }
    ]
  },
  { path: '**', redirectTo: 'login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
