package adefault.ubuntu.josdav.josdav;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by android on 7/18/2016.
 */
public class Fcr_fragment extends Ponds3 {

    public Fcr_fragment() {}
    @Override
    public Query getQuery(DatabaseReference databaseReference) {


        return databaseReference.child("user-Ponds").child(getUid());

    }

}
