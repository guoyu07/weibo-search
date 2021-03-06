package org.conan.search.weibo.action.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.conan.search.weibo.action.WeiboActionService;
import org.springframework.stereotype.Service;

import weibo4j.Timeline;
import weibo4j.Users;
import weibo4j.http.ImageItem;
import weibo4j.model.Status;
import weibo4j.model.User;
import weibo4j.model.WeiboException;
import weibo4j.util.URLEncodeUtils;

@Service
public class WeiboActionServiceImpl implements WeiboActionService {

    public Status send(String msg, String token) throws WeiboException {
        Timeline tl = new Timeline();
        tl.client.setToken(token);
        return tl.UpdateStatus(URLEncodeUtils.encodeURL(msg));
    }

    public Status send(String msg, String image, String token) throws WeiboException, IOException {
        Timeline tl = new Timeline();
        tl.client.setToken(token);
        return tl.UploadStatus(URLEncodeUtils.encodeURL(msg), readFileImage(image));
    }

    public Status repost(long sid, String msg, int comment, String token) throws WeiboException {
        Timeline tl = new Timeline();
        tl.client.setToken(token);
        return tl.Repost(String.valueOf(sid), URLEncodeUtils.encodeURL(msg), comment);
    }

    public Status remove(long sid, String token) throws WeiboException {
        Timeline tl = new Timeline();
        tl.client.setToken(token);
        return tl.Destroy(String.valueOf(sid));
    }

//    public Comment comment(long tid, String comment) throws WeiboException {
//        return new Comments().createComment(URLEncodeUtils.encodeURL(comment), String.valueOf(tid));
//    }
//
//    public Comment delComment(long cid) throws WeiboException {
//        return new Comments().destroyComment(String.valueOf(cid));
//    }
//
//    public User follow(long uid) throws WeiboException {
//        return new Friendships().createFriendshipsById(String.valueOf(uid));
//    }
//
//    public User follow(String screen_name) throws WeiboException {
//        return new Friendships().createFriendshipsByName(screen_name);
//    }
//
//    public User unfollow(long uid) throws WeiboException {
//        return new Friendships().destroyFriendshipsDestroyById(String.valueOf(uid));
//    }
//
//    public User unfollow(String screen_name) throws WeiboException {
//        return new Friendships().destroyFriendshipsDestroyByName(screen_name);
//    }

    /**
     * 一次性取最后2000个粉丝,其他的粉丝通过后台去取
     * 
     * TODO:取2000以外的粉丝
     */
//    public List<User> fans(long uid) throws WeiboException {
//        List<User> list = new ArrayList<User>();
//        Friendships fm = new Friendships();
//        long current = SpringService.WEIBO_LOAD_CURSOR;
//        long count = SpringService.WEIBO_LOAD_COUNT_200;
//        int i = 0;
//        do {
//            UserWapper users = fm.getFollowersById(String.valueOf(uid), (int) count, (int) current);
//            for (User u : users.getUsers()) {
//                list.add(u);
//                i++;
//            }
//            if (i > SpringService.WEIBO_LOAD_COUNT_2000)
//                break; // 最大取2000个
//
//            current = users.getNextCursor();
//            count = users.getTotalNumber() - current > SpringService.WEIBO_LOAD_COUNT_200 ? SpringService.WEIBO_LOAD_COUNT_200 : users.getTotalNumber() - current;
//        } while (current != 0);
//        return list;
//    }

    /**
     * 取活跃的粉丝200个
     */
//    public List<User> fansActive(long uid) throws WeiboException {
//        return new Friendships().getFollowersActive(String.valueOf(uid), SpringService.WEIBO_LOAD_COUNT_200).getUsers();
//    }

    /**
     * 一次性取最后2000个粉丝,其他的粉丝通过后台去取
     * 
     * TODO:取2000以外的粉丝
     */
//    public String[] fansIds(long uid) throws WeiboException {
//        return new Friendships().getFollowersIdsById(String.valueOf(uid), SpringService.WEIBO_LOAD_COUNT_2000, SpringService.WEIBO_LOAD_CURSOR);
//    }

//    public String[] bifansIds(long uid) throws WeiboException {
//        return new Friendships().getFriendsBilateralIds(String.valueOf(uid));
//    }

    public User user(long uid, String token) throws WeiboException {
        Users user= new Users();
        user.client.setToken(token);
        return user.showUserById(String.valueOf(uid));
    }

    public User user(String screen, String token) throws WeiboException {
        Users user= new Users();
        user.client.setToken(token);
        return user.showUserByScreenName(screen);
    }

//    public User userByDomain(String domain) throws WeiboException {
//        return new Users().showUserByDomain(domain);
//    }
//
//    public ArrayList<User> users(long[] uids) throws WeiboException {
//        ArrayList<User> list = new ArrayList<User>();
//        for (long uid : uids)
//            list.add(user(uid));
//        return list;
//    }
//
//    public List<Tag> tags(long uid) throws WeiboException {
//        return new Tags().getTags(String.valueOf(uid));
//    }
//
//    public long tag(String tag) throws WeiboException {
//        // TODO
//        // return new Tags().createTags(tag).toString();
//        return 0;
//    }
//
//    public long delTag(int tagId) throws WeiboException {
//        // TODO
//        // return new Tags().destoryTag(tagId);
//        return 0;
//    }
//
//    public String accUid() throws WeiboException {
//        // TODO
//        // return new Account().getUid();
//        return "";
//    }
//
//    public String accPrivacy() throws WeiboException {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    public List<School> accSchools() throws WeiboException {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    public RateLimitStatus accLimit() throws WeiboException {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    public List<Tag> tagsSuggestion() throws WeiboException {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    public String hotUsersSuggestion() throws WeiboException {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    public String hotUsersSuggestion(String category) throws WeiboException {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    public List<Status> hotTweetSuggestion(int type, int isPic) throws WeiboException {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    public String hotFavoriteSuggestion() throws WeiboException {
//        // TODO Auto-generated method stub
//        return null;
//    }

    private ImageItem readFileImage(String filename) throws IOException, WeiboException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filename));
        int len = bufferedInputStream.available();
        byte[] bytes = new byte[len];
        int r = bufferedInputStream.read(bytes);
        if (len != r) {
            bytes = null;
            throw new IOException("Read File Error!!");
        }
        bufferedInputStream.close();
        ImageItem pic = new ImageItem("pic", bytes);
        return pic;
    }

//    @Override
//    public Object addressToGeo(String addr) throws WeiboException {
//        return new GEO().addressToGeo(addr);
//    }

}
