package com.bridgelabz.Spotify_Testing;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;

public class RestAssured_Api_Automation {
    String token;
    String userId;
    String playlistId;
    @BeforeTest
    public void Get_Token(){
        token="Bearer BQCwGzpmSLwnEFQXDPH2p9ZosIt77yy05XVJ4GW5l5hCoR4YvPzI0royy733Q8YNfJkGsGjZNe2oxkF" +
                "1SKw8d2lX1XD37l3K0YHd14rh8XRGbXApXimP__8QYpP5NOjgmlFMO7lfi4wN3iugcZgP-E6Bnj-w5lK5B7Tyti2" +
                "XI099UQpVnzB7MKwo5XISphoKvYnXlxttNbmaU67kQIlTJPcJIZigLsYAUBG7_pOIq_jovP49Z7y3RTQDS_iZ6kg" +
                "4rG1pNhS4EfU5e_fPyjf98t1bBWiXCTv8kfj5kU1mxvfk4WNqBEecNz7RU4oYpTBXQ9lJ1Lrdv4mp6g";
    }

    // UserProfile
    @Test
    public void Get_Current_UserProfile(){
        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .when()
                .get("\thttps://api.spotify.com/v1/me");
        response.prettyPrint();
        userId=response.path("id");
        System.out.println("UserId :"+userId);
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Get_UserProfile() {
        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .when()
                .get("\thttps://api.spotify.com/v1/users/" + userId);
        response.prettyPrint();
        String g_userId = response.path("id");
        System.out.println("UserId :" + g_userId);
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200, response.getStatusCode());
    }

    // Playlist
    @Test
    public void Playlist_Creation() {
        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .body("{\"name\":\"Vijaya playllist\",\"description\":\"New playlist description\",\"public\":false}")
                .when()
                .post("https://api.spotify.com/v1/users/" + userId + "/playlists");
        response.prettyPrint();
        playlistId = response.path("id");
        System.out.println("Playlist Id :" + playlistId);
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(201);
        Assert.assertEquals(201, response.getStatusCode());
    }
    @Test
    public void Playlist_NewAdd_Item(){
        Response response=given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .body("{\"uris\":[\"spotify:track:5o3jMYOSbaVz3tkgwhELSV\",\"spotify:track:4Cy0NHJ8Gh0xMdwyM9RkQm\",\"spotify:track:2E2znCPaS8anQe21GLxcvJ\"]}")
                .when()
                .post("https://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
        response.prettyPrint();
        String snapshot_id = response.path("snapshot_id");
        System.out.println("Snapshot Id :" + snapshot_id);
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(201);
        Assert.assertEquals(201, response.getStatusCode());
    }
    @Test
    public void Playlist_Get_Playlist(){
        Response response=given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/playlists/"+playlistId);
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Playlist_Get_CurrentUser_Playlist(){
        Response response=given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/me/playlists");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Playlist_Get_Cover_Image(){
        Response response=given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/playlists/"+playlistId+"/images");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Playlist_Get_Playlist_Item(){
        Response response=given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("\thttps://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
        response.prettyPrint();
        int total_playlist_item=response.path("total");
        System.out.println("Total Playlist item :"+total_playlist_item);
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Playlist_Get_User_Playlist(){
        Response response=given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("\thttps://api.spotify.com/v1/users/"+userId+"/playlists");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Playlist_Update_Item(){
        Response response=given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .body("{\"range_start\":2,\"insert_before\":5,\"range_length\":3}")
                .when()
                .put("\thttps://api.spotify.com/v1/playlists/5TBc1fBToUL5ogygLsdPIU/tracks");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Playlist_Update_Playlist_Detail(){
        Response response=given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .body("{\"name\":\"Kanna playlist\",\"description\":\"Updated playlist description\",\"public\":false}")
                .when()
                .put("https://api.spotify.com/v1/playlists/"+playlistId);
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Playlist_U_Relete_Item(){
        Response response=given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .body("{\"tracks\":[{\"uri\":\"spotify:track:4iV5W9uYEdYUVa79Axb7Rh\"},{\"uri\":\"spotify:track:1301WleyT98MSxVHPZCA6M\"}]}")
                .when()
                .delete("	https://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }

     // Search

    @Test
    public void Search_Method(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("q","artist")
                .pathParam("type","track")
                .when()
                .get("https://api.spotify.com/v1/search?q={q}&type={type}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }

    // Tracks
    @Test
    public void Tracks_Get_Tracks_Audio_Analysis(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/audio-analysis/3czhw7W0PTAIROkL1tcsKX");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Tracks_Get_Tracks_Audio_Features(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("ids","3czhw7W0PTAIROkL1tcsKX,3czhw7W0PTAIROkL1tcsKX")
                .when()
                .get("https://api.spotify.com/v1/audio-features?ids={ids}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Tracks_Get_Trackss_Audio_Features(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("ids","3czhw7W0PTAIROkL1tcsKX")
                .when()
                .get("https://api.spotify.com/v1/audio-features?ids={ids}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Tracks_Get_Several_Tracks(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("ids","3czhw7W0PTAIROkL1tcsKX,5o3jMYOSbaVz3tkgwhELSV")
                .when()
                .get("https://api.spotify.com/v1/tracks?ids={ids}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Tracks_Get_Track(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("ids","3czhw7W0PTAIROkL1tcsKX")
                .when()
                .get("https://api.spotify.com/v1/tracks/{ids}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
// Shows
    @Test
    public void Shows_Get_Show(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("id","38bS44xjbVVZ3No3ByF1dJ")
                .when()
                .get("https://api.spotify.com/v1/shows/{id}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Shows_Get_Show_Episodes(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("id","38bS44xjbVVZ3No3ByF1dJ")
                .when()
                .get("https://api.spotify.com/v1/shows/{id}/episodes");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Shows_Get_Several_Shows(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("ids","38bS44xjbVVZ3No3ByF1dJ,38bS44xjbVVZ3No3ByF1dJ")
                .when()
                .get("https://api.spotify.com/v1/shows?ids={ids}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    // Personalization
    @Test
    public void Personalization_Get_Users_Top_Items(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("type","artists")
                .when()
                .get("https://api.spotify.com/v1/me/top/{type}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
// Markets
@Test
public void Markets_Get_Available_Markets(){
    Response response = given()
            .header("Accept","application/json")
            .header("Content-Type","application/json")
            .header("Authorization",token)
            .when()
            .get("https://api.spotify.com/v1/markets");
    response.prettyPrint();
    System.out.println("------------------------------------------------");
    response.then().assertThat().statusCode(200);
    Assert.assertEquals(200,response.getStatusCode());
}
//Episode
@Test
public void Episode_Get_Episode() {
    Response response = given()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .header("Authorization", token)
            .pathParam("id","512ojhOuo1ktJprKbVcKyQ")
            .when()
            .get("https://api.spotify.com/v1/episodes/{id}");
    response.prettyPrint();
    System.out.println("------------------------------------------------");
    response.then().assertThat().statusCode(200);
    Assert.assertEquals(200, response.getStatusCode());
}
    @Test
    public void Episodes_Get_Several_Episode() {
        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .pathParam("ids","512ojhOuo1ktJprKbVcKyQ,512ojhOuo1ktJprKbVcKyQ")
                .when()
                .get("https://api.spotify.com/v1/episodes?ids={ids}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200, response.getStatusCode());
    }
// Browse
@Test
public void Browse_Get_Available_Genre_Seeds(){
    Response response = given()
            .header("Accept","application/json")
            .header("Content-Type","application/json")
            .header("Authorization",token)
            .when()
            .get("https://api.spotify.com/v1/recommendations/available-genre-seeds");
    response.prettyPrint();
    System.out.println("------------------------------------------------");
    response.then().assertThat().statusCode(200);
    Assert.assertEquals(200,response.getStatusCode());
}
    @Test
    public void Browse_Get_Several_Browse_Categories(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/browse/categories");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Browse_Get_Featured_Playlists(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/browse/featured-playlists");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Browse_Get_New_Releases(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/browse/new-releases");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Browse_Get_Recommendations(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("seed_artists","4NHQUGzhtTLFvgF5SZesLK")
                .pathParam("seed_genres","classical,country")
                .pathParam("seed_tracks","0c6xIDDpzE81m2q797ordA")

                .when()
                .get("https://api.spotify.com/v1/recommendations?seed_artists={seed_artists}&seed_genres={seed_genres}&seed_tracks={seed_tracks}");

        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    // Artists
    @Test
    public void Artists_Get_Artists_Albums(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("id","5I8r2w4hf7OYp2cunjihxJ")
                .when()
                .get("https://api.spotify.com/v1/artists/{id}/albums");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Artists_Get_Artists_Related_Artists(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("id","5I8r2w4hf7OYp2cunjihxJ")
                .when()
                .get("https://api.spotify.com/v1/artists/{id}/related-artists");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Artists_Get_Artist(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("id","5I8r2w4hf7OYp2cunjihxJ")
                .when()
                .get("https://api.spotify.com/v1/artists/{id}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Artists_Get_Several_Artists(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("ids","6eSdhw46riw2OUHgMwR8B5,5I8r2w4hf7OYp2cunjihxJ")
                .when()
                .get("https://api.spotify.com/v1/artists?ids={ids}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    // Albums
    @Test
    public void Albums_Get_Album_Tracks(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("id","2pANdqPvxInB0YvcDiw4ko")
                .when()
                .get("https://api.spotify.com/v1/albums/{id}/tracks");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Albums_Get_Album(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("id","2pANdqPvxInB0YvcDiw4ko")
                .when()
                .get("https://api.spotify.com/v1/albums/{id}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Albums_Get_Several_Albums(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("ids","2pANdqPvxInB0YvcDiw4ko,6nlfkk5GoXRL1nktlATNsy,4hnqM0JK4CM1phwfq1Ldyz")
                .when()
                .get("https://api.spotify.com/v1/albums?ids={ids}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    // Follow
    @Test
    public void Follow_Follows_Artists_or_Users(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("type","artist")
                .pathParam("ids","6eSdhw46riw2OUHgMwR8B5,5I8r2w4hf7OYp2cunjihxJ")
                .when()
                .get("https://api.spotify.com/v1/me/following/contains?type={type}&ids={ids}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Follow_Get_Followed_Artists(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("type","artist")
                .when()
                .get("https://api.spotify.com/v1/me/following?type={type}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Follow_Check_if_Users_Follow_Playlist(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("type","artist")
                .when()
                .get("https://api.spotify.com/v1/me/following?type={type}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Follow_Playlist(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("playlist_id","5TBc1fBToUL5ogygLsdPIU")
                .body("{\"public\":false}")
                .when()
                .put("https://api.spotify.com/v1/playlists/{playlist_id}/followers");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Follow_Unfollow_Playlist(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("id","3cEYpjA9oz9GiPac4AsH4n")
                .when()
                .delete("https://api.spotify.com/v1/playlists/{id}/followers");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    // Library
    @Test
    public void Library_Check_Saved_Albums(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("ids","2pANdqPvxInB0YvcDiw4ko,6nlfkk5GoXRL1nktlATNsy,4hnqM0JK4CM1phwfq1Ldyz")
                .when()
                .get("https://api.spotify.com/v1/me/albums/contains?ids={ids}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Library_CheckUsers_Saved_Episodes(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("ids","512ojhOuo1ktJprKbVcKyQ,512ojhOuo1ktJprKbVcKyQ")
                .when()
                .get("https://api.spotify.com/v1/me/episodes/contains?ids={ids}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Library_CheckUsers_Saved_Shows(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("ids","512ojhOuo1ktJprKbVcKyQ,512ojhOuo1ktJprKbVcKyQ")
                .when()
                .get("https://api.spotify.com/v1/me/episodes/contains?ids={ids}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Library_CheckUsers_Saved_Tracks(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("ids","5o3jMYOSbaVz3tkgwhELSV,4Cy0NHJ8Gh0xMdwyM9RkQm")
                .when()
                .get("https://api.spotify.com/v1/me/tracks/contains?ids={ids}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Library_Get_Users_Saved_Albums(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/me/albums");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Library_Get_Users_Saved_Episodes(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/me/episodes");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Library_Get_Users_Saved_Shows(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/me/shows");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Library_Get_Users_Saved_Tracks(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/me/tracks");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Library_Save_Albums(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("ids","2pANdqPvxInB0YvcDiw4ko,6nlfkk5GoXRL1nktlATNsy,4hnqM0JK4CM1phwfq1Ldyz")
                .body("{\"ids\":[\"2pANdqPvxInB0YvcDiw4ko\",\"6nlfkk5GoXRL1nktlATNsy\"]}")
                .when()
                .put("https://api.spotify.com/v1/me/albums?ids={ids}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Library_Save_Episodes_for_User(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("ids","512ojhOuo1ktJprKbVcKyQ,512ojhOuo1ktJprKbVcKyQ")
                .body("{\"ids\":[\"512ojhOuo1ktJprKbVcKyQ\",\"512ojhOuo1ktJprKbVcKyQ\"]}")
                .when()
                .put("https://api.spotify.com/v1/me/episodes?ids={ids}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Library_RemoveAlbums(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("ids","2pANdqPvxInB0YvcDiw4ko,6nlfkk5GoXRL1nktlATNsy,4hnqM0JK4CM1phwfq1Ldyz")
                .body("{\"ids\":[\"2pANdqPvxInB0YvcDiw4ko\",\"6nlfkk5GoXRL1nktlATNsy\",\"4hnqM0JK4CM1phwfq1Ldyz\"]}")
                .when()
                .delete("https://api.spotify.com/v1/me/albums?ids={ids}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    @Test
    public void Library_Remove_Users_Saved_Episodes(){
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .pathParam("ids","512ojhOuo1ktJprKbVcKyQ,512ojhOuo1ktJprKbVcKyQ")
                .body("{\"ids\":[\"512ojhOuo1ktJprKbVcKyQ\",\"512ojhOuo1ktJprKbVcKyQ\"]}")
                .when()
                .delete("https://api.spotify.com/v1/me/episodes?ids={ids}");
        response.prettyPrint();
        System.out.println("------------------------------------------------");
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    //PetStore image test
    @Test
    public void PetStore_Uplode_image(){
        File file=new File("C:\\Users\\user\\Desktop\\Bank\\Dog.htm");
        Response response = given()
                .accept("application/json")
                .contentType("multipart/form-data")
                .multiPart(file)
                .when()
                .post("https://petstore.swagger.io/v2/pet/101/uploadImage");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }
}
