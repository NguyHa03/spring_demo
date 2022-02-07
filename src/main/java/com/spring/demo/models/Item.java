package com.spring.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "item", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Item {

  // Primary Key.
  @Id
  @GenericGenerator(name = "item-uuid-generator", strategy = "uuid")
  @GeneratedValue(generator = "item-uuid-generator")
  @Column(name = "id", nullable = false)
  private String id;

  private int amount;
  private String name;

  // ID-less constructor, mostly used by DummyDataConfig.java for dummy data insertion
  public Item(int amount, String name) {
    this.amount = amount;
    this.name = name;
  }

}
