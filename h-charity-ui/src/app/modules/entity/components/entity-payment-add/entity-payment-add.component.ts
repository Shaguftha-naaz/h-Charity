import { Component, Input, OnInit, inject } from '@angular/core';
import { UploadEvent } from 'primeng/fileupload';
import { FileService } from '../../services/file.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-entity-payment-add',
  templateUrl: './entity-payment-add.component.html',
  styleUrls: ['./entity-payment-add.component.css'],
})
export class EntityPaymentAddComponent implements OnInit {
  @Input() entity: number;

  fileService = inject(FileService);
  messageService = inject(MessageService);

  ngOnInit() {}

  onUpload(event: UploadEvent) {
    this.fileService.uploadQRCode(this.entity, event['files'][0]).subscribe({
      next: res => {
        if (res && res['status'] === 200) {
          this.messageService.add({
            severity: 'success',
            summary: 'Save',
            detail: 'QR code updated successfully',
          });
        }
      },
      error: error => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: error['error'].message,
        });
      },
    });
  }
}
