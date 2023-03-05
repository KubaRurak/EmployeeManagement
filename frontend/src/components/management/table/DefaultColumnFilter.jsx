function DefaultColumnFilter({
    column: { filterValue, setFilter },
}) {

    return (
        <div style={{ display: 'flex', justifyContent: 'center' }}>
            <input
                className="form-control"
                value={filterValue || ''}
                onChange={e => {
                    setFilter(e.target.value || undefined)
                }}
                placeholder={`Filter`}
                style={{ minWidth: '100px'}}
                onClick={(e) => {
                    e.stopPropagation()}
                }
            />
        </div>
    )
}

export default DefaultColumnFilter
