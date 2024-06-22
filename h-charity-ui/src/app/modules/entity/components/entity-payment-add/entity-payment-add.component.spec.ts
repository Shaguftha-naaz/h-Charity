/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { EntityPaymentAddComponent } from './entity-payment-add.component';

describe('EntityPaymentAddComponent', () => {
  let component: EntityPaymentAddComponent;
  let fixture: ComponentFixture<EntityPaymentAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EntityPaymentAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EntityPaymentAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
