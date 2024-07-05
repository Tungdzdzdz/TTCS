package com.example.project1.Response;

import com.example.project1.Model.MatchDetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchDetailResponse {
    private MatchDetail matchDetail;
    private Type type;
    public enum Type {
        CREATE,
        UPDATE,
        DELETE
    }
}
