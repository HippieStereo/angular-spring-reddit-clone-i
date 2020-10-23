import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SignupComponent } from './auth/signup/signup.component';
import { LoginComponent } from './auth/login/login.component';
import { HomeComponent } from './home/home.component';
import { CreatePostComponent } from './post/create-post/create-post.component';
import { CreateSubredditComponent } from './subreddit/create-subreddit/create-subreddit.component';
import { ListSubredditsComponent } from './subreddit/list-subreddits/list-subreddits.component';
import { ViewPostComponent } from './post/view-post/view-post.component';
import { AuthGuard } from './auth/auth.guard';
import { UserProfileComponent } from './auth/user-profile/user-profile.component';

const routes: Routes = [
  {path: 'signup', component: SignupComponent},
  {path: 'view-post/:id', component: ViewPostComponent},
  {path: 'login', component: LoginComponent},
  {path: 'user-profile/:name', component: UserProfileComponent, canActivate: [AuthGuard]},
  {path: '', component: HomeComponent},
  {path: 'create-post', component: CreatePostComponent, canActivate: [AuthGuard]},
  {path: 'create-subreddit', component: CreateSubredditComponent, canActivate: [AuthGuard]},
  {path: 'list-subreddits', component: ListSubredditsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
