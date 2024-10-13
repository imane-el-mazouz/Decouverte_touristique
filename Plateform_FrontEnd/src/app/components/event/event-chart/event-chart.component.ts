import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { Chart, registerables } from 'chart.js';

@Component({
  selector: 'app-event-chart',
  templateUrl: './event-chart.component.html',
  styleUrls: ['./event-chart.component.scss'],
  standalone: true
})

export class EventChartComponent implements OnInit, OnChanges {
  @Input() events: any[] = [];
  public chart: any;

  ngOnInit() {
    Chart.register(...registerables);
    this.createChart();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['events'] && !changes['events'].firstChange) {
      this.updateChart();
    }
  }

  createChart() {
    const eventNames = this.events.map(event => event.name);
    const eventRatings = this.events.map(event => event.rating);

    this.chart = new Chart('canvas', {
      type: 'bar',
      data: {
        labels: eventNames,
        datasets: [
          {
            label: 'Event Ratings',
            data: eventRatings,
            backgroundColor: '#fd7401',
            borderColor: '#fd7401',
            borderWidth: 1,
          },
        ],
      },
      options: {
        scales: {
          y: {
            beginAtZero: true,
          },
        },
      },
    });
  }

  updateChart() {
    const eventNames = this.events.map(event => event.name);
    const eventRatings = this.events.map(event => event.rating);

    if (this.chart) {
      this.chart.data.labels = eventNames;
      this.chart.data.datasets[0].data = eventRatings;
      this.chart.update();
    } else {
      this.createChart();
    }
  }
}
