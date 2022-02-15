import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CreatePrenotationPage } from './create-prenotation.page';

const routes: Routes = [
  {
    path: '',
    component: CreatePrenotationPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CreatePrenotationPageRoutingModule {}
