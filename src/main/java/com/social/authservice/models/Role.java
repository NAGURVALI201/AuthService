package com.social.authservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role extends BaseModel{
    private String name;

    // If needed, we can add list of permissions
}
