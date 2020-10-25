package acp.example.myapplication2.Logic;

public class IncLisPreItem {
    private int mintId;
    private String mtxtListPrep;

    public IncLisPreItem() {
    }

    public IncLisPreItem(int mintId, String txtListPrep){
        this.mintId = mintId;
        this.mtxtListPrep = txtListPrep;

    }

    public int getMintId() {
        return mintId;
    }

    public void setMintId(int mintId) {
        this.mintId = mintId;
    }

    public String getMtxtListPrep() {
        return mtxtListPrep;
    }

    public void setMtxtListPrep(String mtxtListPrep) {
        this.mtxtListPrep = mtxtListPrep;
    }

}
