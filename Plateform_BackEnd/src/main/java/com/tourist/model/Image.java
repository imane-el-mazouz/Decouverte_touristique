//package com.tourist.model;
//
//import com.tourist.model.Room;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//public class Image {
//    @Id
//    @GeneratedValue
//    private Integer id;
//    private String imageUrl;
//    private String cloudinaryImageId;
//    @ManyToOne
//    @JoinColumn(name = "room_id")
//    private Room room;
//
//    public String getUrl() {
//        return imageUrl;
//    }
//
//}
