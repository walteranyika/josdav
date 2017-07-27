package adefault.ubuntu.josdav.josdav;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by android on 7/18/2016.
 */
public class FishPonds extends Ponds {

    public FishPonds() {}
    @Override
    public Query getQuery(DatabaseReference databaseReference) {


        return databaseReference.child("my-Ponds").child(getUid());

    }

}
