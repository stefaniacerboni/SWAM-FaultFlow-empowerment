import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-analyze-sidebar',
  templateUrl: './analyze-sidebar.component.html',
  styleUrls: ['./analyze-sidebar.component.css']
})
export class AnalyzeSidebarComponent {
  @Input() isOpen = false;
  @Input() system: any;
  @Output() closeSidebar = new EventEmitter<void>();
  @Output() analyzeSystem = new EventEmitter<any>();

  timelimit: number = 0;
  timestep: number = 0;
  errorTolerance: number = 0;
  rewards: string = '';
  analysisType: string = 'stochastic';

  close() {
    this.closeSidebar.emit();
  }

  analyze() {
    const analysisParameters = {
      timelimit: this.timelimit,
      timestep: this.timestep,
      errorTolerance: this.errorTolerance,
      rewards: this.rewards,
      analysisType: this.analysisType
    };
    this.analyzeSystem.emit(analysisParameters);
    this.close();
  }
}
