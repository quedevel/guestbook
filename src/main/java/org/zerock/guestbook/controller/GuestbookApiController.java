package org.zerock.guestbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.service.GuestbookService;

@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class GuestbookApiController {

    @Autowired
    private GuestbookService guestbookService;

    @GetMapping("/guest/getGuestbookList")
    public ResponseEntity<PageResultDTO<GuestbookDTO, Guestbook>> getGuestbookList(PageRequestDTO pageRequestDTO){
        return new ResponseEntity<>(guestbookService.getList(pageRequestDTO), HttpStatus.OK);
    }
}
