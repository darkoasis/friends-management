package sg.com.spgroup.friendsmgmt.model;

public interface Views {
    public static interface Status {
    }

    public static interface Friends {
    }

    public static interface Count {
    }

    public static interface Recepients {
    }

    public static interface EmailList {
    }

    public static interface FriendsList extends Status, Friends, Count {

    }

    public static interface UpdateList extends Status, Recepients {

    }
}
