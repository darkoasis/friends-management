package sg.com.spgroup.friendsmgmt.model;

public class ErrorMessage
{
    private String error;

    public ErrorMessage()
    {
    }

    public ErrorMessage( final String error )
    {
        this();
        this.error = error;
    }

    public String getError()
    {
        return error;
    }

    public void setError( final String error )
    {
        this.error = error;
    }
}
