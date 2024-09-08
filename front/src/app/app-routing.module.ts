import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { LoginComponent } from './components/login/login.component';
import { ArticleFeedComponent } from './pages/article-feed/article-feed.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { ThemesComponent } from './pages/themes/themes.component';
import { AuthGuard } from './common/guards/auth.guard';
import { ArticleCardComponent } from './components/article-card/article-card.component';
import { CreateArticleComponent } from './components/create-article/create-article.component';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: 'home', component: HomeComponent },        
  { path: 'sign-up', component: SignUpComponent },        
  { path: 'article-card', component: ArticleCardComponent },        
  { path: 'create-article', component: CreateArticleComponent },       
  { path: 'login', component: LoginComponent },          
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },  
  { path: 'article-feed', component: ArticleFeedComponent, canActivate: [AuthGuard] },  
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },  
  { path: 'themes', component: ThemesComponent, canActivate: [AuthGuard] },    
  { path: '**', redirectTo: '/home' }  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
