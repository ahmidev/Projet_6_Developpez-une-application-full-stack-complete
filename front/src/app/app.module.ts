import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { CreateArticleComponent } from './components/create-article/create-article.component';
import { ArticleCardComponent } from './components/article-card/article-card.component';
import { ThemeCardComponent } from './components/theme-card/theme-card.component';
import { ProfilFormComponent } from './components/profil-form/profil-form.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { LoginComponent } from './components/login/login.component';
import { ArticleFeedComponent } from './pages/article-feed/article-feed.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { ThemesComponent } from './pages/themes/themes.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http'
import { ToastrModule } from 'ngx-toastr';

@NgModule({
  declarations: [AppComponent, HomeComponent, NavbarComponent, CreateArticleComponent, ArticleCardComponent, ThemeCardComponent, ProfilFormComponent, SignUpComponent, LoginComponent, ArticleFeedComponent, ProfileComponent, ThemesComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    MatButtonModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
