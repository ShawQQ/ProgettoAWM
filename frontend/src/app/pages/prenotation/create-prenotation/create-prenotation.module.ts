import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CreatePrenotationPageRoutingModule } from './create-prenotation-routing.module';

import { CreatePrenotationPage } from './create-prenotation.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
	ReactiveFormsModule,
    IonicModule,
    CreatePrenotationPageRoutingModule,
  ],
  declarations: [CreatePrenotationPage]
})
export class CreatePrenotationPageModule {}
