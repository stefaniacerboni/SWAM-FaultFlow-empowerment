import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-json-sidebar',
  templateUrl: './json-sidebar.component.html',
  styleUrls: ['./json-sidebar.component.css']
})
export class JsonSidebarComponent {
  @Input() isOpen = false;
  @Input() jsonText = '';
  @Output() closeSidebar = new EventEmitter<void>();
  @Output() saveJson = new EventEmitter<string>();

  close() {
    this.closeSidebar.emit();
  }

  save() {
    this.saveJson.emit(this.jsonText);
  }
}
