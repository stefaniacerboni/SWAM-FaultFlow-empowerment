import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  recentActivities: string[] = [];

  ngOnInit(): void {
    this.loadRecentActivities();
  }

  loadRecentActivities(): void {
    // Mock data - replace with actual data retrieval logic
    this.recentActivities = [
      'Edited system: System A',
      'Imported data file: data-file-2024.json',
      'Viewed image: System B Fault Tree',
      'Downloaded Petri Net for System C'
    ];
  }
}
