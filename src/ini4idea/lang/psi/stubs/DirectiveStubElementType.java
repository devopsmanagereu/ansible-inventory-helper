package ini4idea.lang.psi.stubs;

import com.intellij.psi.stubs.*;
import com.intellij.util.io.StringRef;
import ini4idea.IniElementTypes;
import ini4idea.lang.psi.DirectiveKeyIndex;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @author Alexei Vasin
 */
public class DirectiveStubElementType extends IStubElementType<DirectiveStub, Directive> {
    public DirectiveStubElementType() {
        super("INI", IniElementTypes.LANG);
    }

/*
    public DirectiveStubElementType(@NotNull @org.jetbrains.annotations.NonNls String debugName, @org.jetbrains.annotations.Nullable Language language) {
        super(debugName, language);
    }
*/

    @NotNull
    @Override
    public Directive createPsi(@NotNull DirectiveStub stub) {
        return new DirectiveImpl(stub, this);
    }

    @NotNull
    @Override
    public DirectiveStub createStub(@NotNull Directive psi, StubElement parentStub) {
        return new DirectiveStubImpl(parentStub, psi.getKey());
    }

    @NotNull
    @Override
    public String getExternalId() {
        return "ini.prop";
    }

    @Override
    public void serialize(@NotNull DirectiveStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getKey());
    }

    @NotNull
    @Override
    public DirectiveStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        final StringRef ref = dataStream.readName();
        return new DirectiveStubImpl(parentStub, ref.getString());
    }

    @Override
    public void indexStub(@NotNull DirectiveStub stub, @NotNull IndexSink sink) {
        sink.occurrence(DirectiveKeyIndex.KEY, stub.getKey());
    }
}
