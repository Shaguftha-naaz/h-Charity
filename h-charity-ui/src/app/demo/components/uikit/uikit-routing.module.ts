import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'list',
        data: { breadcrumb: 'List' },
        loadChildren: () =>
          import('./list/listdemo.module').then(m => m.ListDemoModule),
      },
      {
        path: 'media',
        data: { breadcrumb: 'Media' },
        loadChildren: () =>
          import('./media/mediademo.module').then(m => m.MediaDemoModule),
      },
      {
        path: 'table',
        data: { breadcrumb: 'Table' },
        loadChildren: () =>
          import('./table/tabledemo.module').then(m => m.TableDemoModule),
      },

      { path: '**', redirectTo: '/notfound' },
    ]),
  ],
  exports: [RouterModule],
})
export class UIkitRoutingModule {}
