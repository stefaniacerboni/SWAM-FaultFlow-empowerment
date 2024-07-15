import { Component, OnInit } from '@angular/core';
import { SystemsService } from '../../core/services/systems.service';
import {OutputSystemDto} from "../../core/models/output-system-dto";

@Component({
  selector: 'app-systems',
  templateUrl: './systems.component.html',
  styleUrls: ['./systems.component.css']
})
export class SystemsComponent implements OnInit {
  systems: OutputSystemDto[] = [];
  isSidebarOpen = false;
  isAnalyzeSidebarOpen = false;
  jsonText = '';
  selectedSystem: OutputSystemDto | null = null;

  constructor(private systemsService: SystemsService) {}

  ngOnInit(): void {
    this.loadSystems();
  }

  loadSystems(): void {
    this.systemsService.getSystems().subscribe(
      (data: OutputSystemDto[]) => {
        this.systems = data;
      },
      error => {
        console.error('Error loading systems', error);
      }
    );
  }

  downloadAsPetrinet(system: OutputSystemDto): void {
    // Implement download logic here
    this.systemsService.downloadPetrinet(system.uuid).subscribe(
      response => {
        // Handle file download response
      },
      error => {
        console.error('Error downloading PetriNet', error);
      }
    );
  }

  viewAsJson(system: OutputSystemDto): void {
    this.jsonText = JSON.stringify(system, null, 2);
    this.isSidebarOpen = true;
  }

  viewImage(system: OutputSystemDto): void {
    this.systemsService.getSystemImage(system.uuid).subscribe(
      imageUrl => {
        const imageWindow = window.open("");
        // @ts-ignore
        imageWindow.document.write(`<img src="${imageUrl}" />`);
      },
      error => {
        console.error('Error fetching system image', error);
      }
    );
  }

  closeSidebar(): void {
    this.isSidebarOpen = false;
  }

  saveJson(json: string): void {
    try {
      const updatedSystem = JSON.parse(json);
      // Call your service to save the updated system if necessary
      console.log('Updated System:', updatedSystem);
    } catch (error) {
      console.error('Invalid JSON:', error);
    }
  }

  openAnalyzeSidebar(system: OutputSystemDto): void {
    this.selectedSystem = system;
    this.isAnalyzeSidebarOpen = true;
  }

  closeAnalyzeSidebar(): void {
    this.isAnalyzeSidebarOpen = false;
    this.selectedSystem = null;
  }

  performAnalysis(parameters: any): void {
    console.log('Performing analysis with parameters:', parameters);
    // Call your service to perform the analysis with the specified parameters
  }

}
