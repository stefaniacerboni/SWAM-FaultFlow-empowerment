import { Component } from '@angular/core';
import { ImportService } from '../../core/services/import.service';

@Component({
  selector: 'app-import',
  templateUrl: './import.component.html',
  styleUrls: ['./import.component.css']
})
export class ImportComponent {
  jsonInput: string = '';

  constructor(private importService: ImportService) {}

  onSubmit() {
    this.importService.importJson(this.jsonInput).subscribe(
      response => {
        console.log('Import successful', response);
        // Handle success response
      },
      error => {
        console.error('Import error', error);
        // Handle error response
      }
    );
  }
}
